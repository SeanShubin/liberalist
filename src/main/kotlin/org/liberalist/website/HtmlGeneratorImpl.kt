package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import org.liberalist.website.tree.Tree
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths

class HtmlGeneratorImpl(val baseSource: Path,
                        val baseGenerated: Path,
                        val files: FilesContract,
                        val markdownToHtmlConverter: MarkdownToHtmlConverter,
                        val charset: Charset) : HtmlGenerator {
    override fun generateHtml(sources: Tree<Path>): Tree<HtmlConversion> =
            sources.map(::generateHtml)

    private fun generateHtml(path: Path): HtmlConversion =
            if (files.isDirectory(path)) {
                generateHtmlDir(path)
            } else {
                generateHtmlFile(path)
            }

    private fun generateHtmlFile(markdownPath: Path): HtmlConversion {
        val relativePath = baseSource.relativize(markdownPath)
        val nameParts = toNameParts(relativePath)
        val htmlPath = baseGenerated.resolve(Paths.get(nameParts.joinToString("/") + ".html"))
        val name = nameParts[nameParts.size - 1]
        val markdown = files.readString(markdownPath, charset)
        val (title, html) = markdownToHtmlConverter.markdownToHtml(markdown)
        writeString(htmlPath, html, charset)
        return HtmlConversionFile(markdownPath, htmlPath, name, title)
    }

    private fun writeString(path: Path, text: String, charset: Charset) {
        files.createDirectories(path.parent)
        files.writeString(path, text, charset)
    }

    private fun generateHtmlDir(path: Path): HtmlConversion =
            HtmlConversionDir(path, path.fileName.toString())

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