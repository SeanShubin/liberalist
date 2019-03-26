package org.liberalist.website

import org.liberalist.website.tree.Tree

interface HtmlGenerator {
    fun generateHtml(): Tree<HtmlConversion>
}