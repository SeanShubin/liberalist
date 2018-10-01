package org.liberalist.website

import java.lang.RuntimeException
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.Predicate
import java.util.stream.Stream

class MarkdownHtmlGenerator(private val sourceMarkdownDir: Path,
                            private val generatedHtmlDir:Path,
                            private val filesContract:FilesContract,
                            private val charset: Charset,
                            private val converter:Converter,
                            private val notifyWrite:(Path, String)->Unit) : HtmlGenerator {
    override fun generateHtml() {
        generateHtmlFromDir(sourceMarkdownDir)
    }

    private fun generateHtmlFromDir(fromDir: Path) {
        val files = filesContract.list(fromDir).filter(markdownFilesOnly)
        generateHtmlFromFiles(files)
        val directories = filesContract.list(fromDir).filter(directoriesOnly)
        directories.forEach(::generateHtmlFromDir)
    }

    private fun generateHtmlFromFiles(files: Stream<Path>) {
        files.forEach(::generateHtmlFromFile)
    }

    private fun generateHtmlFromFile(markdownFile: Path) {
        val relative = sourceMarkdownDir.relativize(markdownFile)
        val htmlFile = replaceExtension(generatedHtmlDir.resolve(relative), "html")
        val markdownContent = readFile(markdownFile)
        val htmlContent:String = converter.markdownToHtml(markdownContent)
        writeFile(htmlFile, htmlContent)
    }

    private fun readFile(path:Path):String {
        val bytes= filesContract.readAllBytes(path)
        val text = String(bytes, charset)
        return text
    }

    private fun writeFile(path:Path, text:String) {
        notifyWrite(path, text)
        val bytes = text.toByteArray(charset)
        val parent = path.parent
        if(!filesContract.exists(parent )){
            filesContract.createDirectories(parent)
        }
        filesContract.write(path, bytes)
    }

    private fun replaceExtension(path:Path, extension:String):Path{
        val pathString = path.toString()
        val lastDotIndex = pathString.lastIndexOf('.')
        if(lastDotIndex == -1){
            throw RuntimeException("Unable to replace extension of $path, there was no dot '.' to indicate where the extension was")
        }
        val newPathString = pathString.substring(0, lastDotIndex+1) + extension
        return Paths.get(newPathString)
    }

    private val markdownFilesOnly: Predicate<Path> = Predicate { path -> filesContract.isRegularFile(path) && path.toString().endsWith(".md") }

    private val directoriesOnly: Predicate<Path> = Predicate { path -> filesContract.isDirectory(path) }
}
