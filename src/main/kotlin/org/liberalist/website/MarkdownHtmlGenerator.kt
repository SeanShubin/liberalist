package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.toList

class MarkdownHtmlGenerator(private val sourceMarkdownDir: Path,
                            private val generatedHtmlDir: Path,
                            private val filesContract: FilesContract,
                            private val charset: Charset,
                            private val markdownToHtmlConverter: MarkdownToHtmlConverter,
                            private val tabToHtmlConverter: TabToHtmlConverter,
                            private val notifyWrite: (Path, String) -> Unit,
                            private val fileFilter: FileFilter) : () -> Unit {
    private val markdownFilesOnly = fileFilter.fileEndsWith(".md")
    private val directoriesOnly = fileFilter.isDirectory
    override fun invoke() {
        val tab = generateHtmlFromDir(sourceMarkdownDir)
        generateTableOfContents(tab)
    }

    private fun generateTableOfContents(tab: Tab) {
        val tableOfContentsFile = generatedHtmlDir.resolve("index.html")
        val text = tabToHtmlConverter.tabToTopLevelHtml("Liberalist", tab)
        writeFile(tableOfContentsFile, text)
    }

    private fun generateHtmlFromDir(fromDir: Path): Tab {
        val files = filesContract.list(fromDir).filter(markdownFilesOnly).sorted().toList()
        val fileTabs: List<Tab> = markdownFilesToHtmlFragments(files)
        val directories = filesContract.list(fromDir).filter(directoriesOnly).sorted().toList()
        val dirTabs: List<Tab> = directories.map(::generateHtmlFromDir)
        val tabPath = TabPath.fromPath(filesContract, sourceMarkdownDir, fromDir)
        val tab = Tab(tabPath, fileTabs + dirTabs)
        return tab
    }

    private fun markdownFilesToHtmlFragments(files: List<Path>): List<Tab> {
        return files.map(::markdownFileToHtmlFragment)
    }

    private fun markdownFileToHtmlFragment(markdownFile: Path): Tab {
        val relative = sourceMarkdownDir.relativize(markdownFile)
        val htmlFile = replaceExtension(generatedHtmlDir.resolve(relative), "html")
        val markdownContent = readFile(markdownFile)
        val (title, htmlContent) = markdownToHtmlConverter.markdownToHtml(markdownContent)
        writeFile(htmlFile, htmlContent)
        val tabPath = TabPath.fromPath(filesContract, sourceMarkdownDir, markdownFile)
        val tab = Tab(tabPath)
        return tab
    }

    private fun readFile(path: Path): String {
        val bytes = filesContract.readAllBytes(path)
        val text = String(bytes, charset)
        return text
    }

    private fun writeFile(path: Path, text: String) {
        notifyWrite(path, text)
        val bytes = text.toByteArray(charset)
        val parent = path.parent
        if (!filesContract.exists(parent)) {
            filesContract.createDirectories(parent)
        }
        filesContract.write(path, bytes)
    }

    private fun replaceExtension(path: Path, extension: String): Path {
        val pathString = path.toString()
        val lastDotIndex = pathString.lastIndexOf('.')
        if (lastDotIndex == -1) {
            throw RuntimeException(
                    "Unable to replace extension of $path, there was no dot '.' to indicate where the extension was")
        }
        val newPathString = pathString.substring(0, lastDotIndex + 1) + extension
        return Paths.get(newPathString)
    }

}
