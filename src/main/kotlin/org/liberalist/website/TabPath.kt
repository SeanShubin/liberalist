package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import java.nio.file.Path
import java.nio.file.Paths

data class TabPath(val basePath: Path, val fullPath: Path, val isDirectory: Boolean) {
    private val relativePath = basePath.relativize(fullPath)
    private val last = relativePath.last()
    val name: String = if (isDirectory) last.toString() else removeExtension(last, "md").toString()
    val href: String
        get() {
            val path = if (isDirectory) relativePath else replaceExtension(relativePath, "md", "html")
            return path.joinToString("/") { it.toString() }
        }

    companion object {
        fun fromPath(files: FilesContract, basePath: Path, fullPath: Path): TabPath {
            val isDirectory = files.isDirectory(fullPath)
            return TabPath(basePath, fullPath, isDirectory)
        }

        private val fileWithExtensionRegex = Regex("""(.*)\.([^\.]*)$""")
        private fun replaceExtension(path: Path, from: String, to: String): Path {
            val matchResult: MatchResult? = fileWithExtensionRegex.matchEntire(path.toString())
            if (matchResult == null) {
                throw RuntimeException("Path '$path' does not match pattern '$fileWithExtensionRegex'")
            } else {
                val (base, extension) = matchResult.destructured
                if (extension.equals(from, ignoreCase = true)) {
                    return Paths.get("$base.$to")
                } else {
                    throw RuntimeException("Path '$path' does not have extension '$from'")
                }
            }
        }

        private fun removeExtension(path: Path, expectedExtension: String): Path {
            val matchResult: MatchResult? = fileWithExtensionRegex.matchEntire(path.toString())
            if (matchResult == null) {
                throw RuntimeException("Path '$path' does not match pattern '$fileWithExtensionRegex'")
            } else {
                val (base, extension) = matchResult.destructured
                if (extension.equals(expectedExtension, ignoreCase = true)) {
                    return Paths.get(base)
                } else {
                    throw RuntimeException("Path '$path' does not have extension '$extension'")
                }
            }
        }
    }
}
