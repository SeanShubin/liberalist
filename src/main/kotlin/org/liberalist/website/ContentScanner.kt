package org.liberalist.website

import org.liberalist.website.tree.Tree
import java.nio.file.Path

interface ContentScanner {
    fun findSources(): Tree<Path>
}