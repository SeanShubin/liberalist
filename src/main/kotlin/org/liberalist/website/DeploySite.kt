package org.liberalist.website

import org.liberalist.website.tree.Tree
import java.nio.file.Path

class DeploySite(private val contentScanner: ContentScanner,
                 private val foundSourcesEvent: (Tree<Path>) -> Unit,
                 private val htmlGenerator: HtmlGenerator,
                 private val htmlGeneratorEvent: (Tree<HtmlConversion>) -> Unit) : Runnable {
    override fun run() {
        val sources = contentScanner.findSources()
        foundSourcesEvent(sources)
        val htmlGeneratorResult = htmlGenerator.generateHtml()
        htmlGeneratorEvent(htmlGeneratorResult)
        
    }
}
