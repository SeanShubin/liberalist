package org.liberalist.website

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.toList

class FileMapper(private val list: (Path) -> List<Path>,
                 private val isDirectory: (Path) -> Boolean,
                 private val acceptFile: (Path) -> Boolean,
                 private val operation: (String, Path, Path) -> Unit) {
    fun map(srcDir: Path, destDir: Path) {
        val filesAndDirectories = list(srcDir).sorted()
        val fileNames = filesAndDirectories.asSequence().filter(acceptFile).map { path -> path.fileName.toString() }.toList()
        fileNames.map(fileOperation(srcDir, destDir))
        val directories = filesAndDirectories.filter(isDirectory)
        directories.map(directoryOperation(srcDir, destDir))
    }

    private fun fileOperation(srcDir: Path, destDir: Path): (String) -> Unit = { fileName ->
        operation(fileName, srcDir, destDir)
    }

    private fun directoryOperation(srcDir: Path, destDir: Path): (Path) -> Unit = { srcSubDir ->
        val destSubDir = destDir.resolve(srcDir.relativize(srcSubDir))
        map(srcSubDir, destSubDir)
    }
}

fun main(args: Array<String>) {
    val list: (Path) -> List<Path> = { path -> Files.list(path).toList() }
    val isDirectory: (Path) -> Boolean = { path -> Files.isDirectory(path) }
    val acceptFile: (Path) -> Boolean = { path -> path.toString().endsWith(".md") }
    val operation: (String, Path, Path) -> Unit = { name, src, dest -> println("$name: $src -> $dest") }
    val fileMapper = FileMapper(list, isDirectory, acceptFile, operation)
    val baseDir = Paths.get(".")
    val srcDir = baseDir.resolve("content")
    val destDir = baseDir.resolve("build/html")
    fileMapper.map(srcDir, destDir)
}