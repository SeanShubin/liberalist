package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class HtmlCssJsFileCopier(private val srcDir: Path,
                          private val destDir: Path,
                          private val files: FilesContract) : () -> Unit {
    override fun invoke() {
        copyFiles(srcDir, destDir)
    }

    private val isHtmlCssJsFile = FileUtil.createHasExtensionFunction("html", "css", "js")
    private val isDirectory = FileUtil.createIsDirectoryFunction(files)
    private fun copyFiles(srcDir: Path, destDir: Path) {
        val files = files.list(srcDir).filter(isHtmlCssJsFile)
        fun copyFileToDir(srcFile: Path) {
            val fileName = srcFile.fileName.toString()
            val destFile = destDir.resolve(fileName)
            this.files.copy(srcFile, destFile, StandardCopyOption.REPLACE_EXISTING)
        }
        files.forEach(::copyFileToDir)
        val srcSubDirectories = this.files.list(srcDir).filter(isDirectory)
        fun processDirectory(srcSubDirectory: Path) {
            val destSubDirectory = destDir.resolve(srcDir.relativize(srcSubDirectory))
            copyFiles(srcSubDirectory, destSubDirectory)
        }
        srcSubDirectories.forEach(::processDirectory)
    }
}
