package org.liberalist.website

import org.liberalist.website.tree.Tree

interface ModelGenerator {
    fun createModel(trees: List<Tree<String>>, titles: Map<String, String>): Map<String, Page>
    data class Tab(val name: String, val title: String, val selected: Boolean)
    data class Page(val tabBars: List<List<Tab>>, val content: String)
}
