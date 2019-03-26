package org.liberalist.website

import org.liberalist.website.tree.Branch

interface ModelGenerator {
    fun createModel(tree: Branch<String>, titles: Map<String, String>): Map<String, Page>
    data class Tab(val name: String, val title: String, val selected: Boolean)
    data class Page(val tabBars: List<List<Tab>>, val content: String)
}
