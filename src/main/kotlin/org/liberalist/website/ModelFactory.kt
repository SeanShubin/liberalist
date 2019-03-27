package org.liberalist.website

import org.liberalist.website.tree.Branch

interface ModelFactory {
    fun createModel(tree: Branch<String>, titles: Map<String, String>): Model
    data class Tab(val name: String, val title: String, val selected: Boolean)
    data class Page(val tabBars: List<List<Tab>>, val content: String)
    data class Model(val home: String, val pages: Map<String, Page>)
}
