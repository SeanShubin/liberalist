package org.liberalist.website

import java.nio.file.Path
import java.util.function.Predicate

class FileFilter(private val filesContract: FilesContract) {
    fun fileEndsWith(s: String): Predicate<Path> =
            Predicate { path -> filesContract.isRegularFile(path) && path.toString().endsWith(s) }

    val isDirectory: Predicate<Path> =
            Predicate { path -> filesContract.isDirectory(path) }

}