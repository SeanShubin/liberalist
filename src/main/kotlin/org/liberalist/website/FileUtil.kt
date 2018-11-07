package org.liberalist.website

import java.nio.file.Path
import kotlin.streams.toList

class FileUtil(files: FilesContract) {
    val listFiles: (Path) -> List<Path> = { path -> files.list(path).toList() }
    val isDirectory: (Path) -> Boolean = { path -> files.isDirectory(path) }
    val isHtmlCssJsFile: (Path) -> Boolean = hasExtension("html", "css", "js")
    val copyFile: (String, Path, Path) -> Unit = { name, srcDir, destDir ->
        val srcFile = srcDir.resolve(name)
        val destFile = destDir.resolve(name)
        files.copy(srcFile, destFile)
    }

    fun hasExtension(vararg extensions: String): (Path) -> Boolean = { path: Path ->
        var result = false
        for (extension in extensions) {
            if (path.fileName.toString().endsWith(extension)) {
                result = true
                break
            }
        }
        result
    }
}
