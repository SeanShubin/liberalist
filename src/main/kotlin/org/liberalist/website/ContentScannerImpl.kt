package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import org.liberalist.website.tree.Branch
import org.liberalist.website.tree.Leaf
import org.liberalist.website.tree.Tree
import java.nio.file.Path
import kotlin.streams.toList

class ContentScannerImpl(private val files: FilesContract,
                         private val basePath: Path) : ContentScanner {
    override fun findSources(): Tree<Path> = findSources(basePath)!!

    private fun findSources(path: Path): Tree<Path>? =
            when {
                isDirectory(path) -> {
                    val sortedFiles = files.list(path).sorted(PathNaturalSort).toList()
                    val children = sortedFiles.map(::findSources)
                    Branch(path, children.filterNotNull())
                }
                isSource(path) -> Leaf(path)
                else -> null
            }

    private fun isDirectory(path: Path): Boolean = files.isDirectory(path)
    private fun isSource(path: Path): Boolean = path.toString().endsWith(".md")
}
