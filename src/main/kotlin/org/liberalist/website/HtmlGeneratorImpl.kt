package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import org.liberalist.website.tree.Tree
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths

class HtmlGeneratorImpl(private val baseSource: Path,
                        private val baseGenerated: Path,
                        private val files: FilesContract,
                        private val markdownToHtmlConverter: MarkdownToHtmlConverter,
                        private val charset: Charset) : HtmlGenerator {
    override fun generateHtml(sources: Tree<Path>): Tree<HtmlConversion> {
        val generated = sources.map(::generateHtml)
        ensureNoDuplicateNames(generated)
        return generated
    }

    private fun ensureNoDuplicateNames(generated: Tree<HtmlConversion>) {
        val nodes = generated.leafNodes()
        val size = nodes.size
        for (i in 0 until size) {
            for (j in i + 1 until size) {
                if (nodes[i].value.name == nodes[j].value.name) {
                    throw RuntimeException(
                            "Duplicate name ${nodes[i].value.original} ${nodes[j].value.original}")
                }
            }
        }
    }

    private fun generateHtml(path: Path): HtmlConversion =
            if (files.isDirectory(path)) {
                generateHtmlDir(path)
            } else {
                generateHtmlFile(path)
            }

    private fun generateHtmlFile(markdownPath: Path): HtmlConversion {
        val relativePath = baseSource.relativize(markdownPath)
        val nameParts = toNameParts(relativePath)
        val htmlPath = baseGenerated.resolve(Paths.get("content/" + nameParts.joinToString("/") + ".html"))
        val name = nameParts[nameParts.size - 1]
        val markdown = files.readString(markdownPath, charset)
        val (title, html) = markdownToHtmlConverter.markdownToHtml(markdown)
        writeString(htmlPath, html, charset)
        return HtmlConversionFile(markdownPath, htmlPath, name, title)
    }

    private fun writeString(path: Path, text: String, charset: Charset) {
        files.createDirectories(path.parent)
        val newText = workaroundToBugWhereSomeCharsWillNotWriteProperlyToFiles(text)
        files.writeString(path, newText, charset)
    }

    private fun workaroundToBugWhereSomeCharsWillNotWriteProperlyToFiles(text: String): String {
        val enDash = '\u2013'
        val leftSingleQuotationMark = '\u2018'
        val rightSingleQuotationMark = '\u2019'
        return text.replace(enDash, '-').replace(leftSingleQuotationMark, '\'').replace(rightSingleQuotationMark, '\'')
    }

    private fun generateHtmlDir(path: Path): HtmlConversion =
            HtmlConversionDir(path, toNamePart(path.fileName))

    private fun toNameParts(path: Path): List<String> {
        return path.toList().map(::toNamePart)
    }

    private fun toNamePart(path: Path): String = removeExtension(removeSortPrefix(path.toString()))

    private fun removeExtension(s: String): String {
        val lastDot = s.lastIndexOf('.')
        return if (lastDot == -1) s
        else s.substring(0, lastDot)
    }

    private fun removeSortPrefix(s: String): String {
        val withSortPrefix = Regex("""^\d+\-(.*)""")
        val matchResult = withSortPrefix.matchEntire(s)
        return matchResult?.destructured?.component1() ?: s

    }
}