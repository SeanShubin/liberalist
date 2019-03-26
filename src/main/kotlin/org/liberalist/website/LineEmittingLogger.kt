package org.liberalist.website

import org.liberalist.website.tree.Tree
import java.nio.file.Path

class LineEmittingLogger(private val emitLine: (String) -> Unit) : Logger {
    override fun foundSources(sources: Tree<Path>) {
        sources.toLines().forEach(emitLine)
    }
}
