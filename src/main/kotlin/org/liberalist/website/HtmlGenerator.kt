package org.liberalist.website

import org.liberalist.website.tree.Tree
import java.nio.file.Path

interface HtmlGenerator {
    fun generateHtml(sources: Tree<Path>): Tree<HtmlConversion>
}
