package org.liberalist.website

import java.nio.file.Path

class ConfigurableFileProcessor(private val listFiles: (Path) -> List<Path>,
                                private val isDirectory: (Path) -> Boolean,
                                private val acceptFile: (Path) -> Boolean,
                                private val operation: (String, Path, Path) -> Unit) : FileProcessor {
    override fun processFiles(srcDir: Path, destDir: Path) {
        val filesAndDirectories = listFiles(srcDir).sorted()
        val fileNames = filesAndDirectories.filter(acceptFile).map { path -> path.fileName.toString() }
        fileNames.map(fileOperation(srcDir, destDir))
        val directories = filesAndDirectories.filter(isDirectory)
        directories.map(directoryOperation(srcDir, destDir))
    }

    private fun fileOperation(srcDir: Path, destDir: Path): (String) -> Unit = { fileName ->
        operation(fileName, srcDir, destDir)
    }

    private fun directoryOperation(srcDir: Path, destDir: Path): (Path) -> Unit = { srcSubDir ->
        val destSubDir = destDir.resolve(srcDir.relativize(srcSubDir))
        processFiles(srcSubDir, destSubDir)
    }
}
