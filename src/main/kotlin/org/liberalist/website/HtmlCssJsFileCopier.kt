package org.liberalist.website

import java.nio.file.Path
import java.nio.file.StandardCopyOption

class HtmlCssJsFileCopier(private val srcDir: Path,
                          private val destDir: Path,
                          private val filesContract: FilesContract) : () -> Unit {
    override fun invoke() {
        copyFiles(srcDir, destDir)
    }

    private val isHtmlCssJsFile = FileUtil.createHasExtensionFunction("html", "css", "js")
    private val isDirectory = FileUtil.createIsDirectoryFunction(filesContract)
    private fun copyFiles(srcDir: Path, destDir: Path) {
        val files = filesContract.list(srcDir).filter(isHtmlCssJsFile)
        fun copyFileToDir(srcFile: Path) {
            val fileName = srcFile.fileName.toString()
            val destFile = destDir.resolve(fileName)
            filesContract.copy(srcFile, destFile, StandardCopyOption.REPLACE_EXISTING)
        }
        files.forEach(::copyFileToDir)
        val srcSubDirectories = filesContract.list(srcDir).filter(isDirectory)
        fun processDirectory(srcSubDirectory: Path) {
            val destSubDirectory = destDir.resolve(srcDir.relativize(srcSubDirectory))
            copyFiles(srcSubDirectory, destSubDirectory)
        }
        srcSubDirectories.forEach(::processDirectory)
    }
}
