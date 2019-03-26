package org.liberalist.website

import org.liberalist.website.tree.Tree
import java.nio.file.Path

class DeploySite(private val contentScanner: ContentScanner,
                 private val foundSources: (Tree<Path>) -> Unit) : Runnable {
    override fun run() {
        val sources = contentScanner.findSources()
        foundSources(sources)
    }
}
