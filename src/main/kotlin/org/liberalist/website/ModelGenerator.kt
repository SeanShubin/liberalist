package org.liberalist.website

import org.liberalist.website.tree.Tree

interface ModelGenerator {
    fun generateModel(htmlGeneratorResult: Tree<HtmlConversion>)
}
