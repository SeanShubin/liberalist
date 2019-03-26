package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import org.liberalist.website.tree.Branch
import org.liberalist.website.tree.Leaf
import org.liberalist.website.tree.Tree
import java.nio.file.Path
import kotlin.streams.toList

class ContentScannerImpl(private val files: FilesContract,
                         private val basePath: Path) : ContentScanner {
    override fun findSources(): List<Tree<Path>> = findSources(basePath)

    private fun findSources(path: Path): List<Tree<Path>> {
        val trees = mutableListOf<Tree<Path>>()
        val sortedFiles = files.list(path).sorted(PathNaturalSort).toList()
        sortedFiles.forEach {
            if (isDirectory(it)) {
                trees.add(Branch(it, findSources(it)))
            } else if (isSource(it)) {
                trees.add(Leaf(it))
            }
        }
        return trees
    }

    private fun isDirectory(path: Path): Boolean = files.isDirectory(path)
    private fun isSource(path: Path): Boolean = path.toString().endsWith(".md")
}
