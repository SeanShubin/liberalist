package org.liberalist.website

import org.liberalist.website.ModelFactory.Page
import org.liberalist.website.ModelFactory.Tab
import org.liberalist.website.tree.Branch

class ModelFactoryImpl : ModelFactory {
    override fun createModel(branch: Branch<String>,
                             titles: Map<String, String>): Map<String, Page> {
        val paths = branch.children.flatMap { it.leafPaths(emptyList()) }
        fun createEntryWithTrees(path: List<String>) = createEntry(path, branch, titles)
        return paths.map(::createEntryWithTrees).toMap()
    }

    private fun createEntry(path: List<String>, branch: Branch<String>, titles: Map<String, String>): Pair<String, Page> {
        val name = path[path.size - 1]
        val content = path.joinToString("/") + ".html"
        val tabBars = createTabBars(path, branch, titles)
        return Pair(name, Page(tabBars, content))
    }

    private fun createTabBars(path: List<String>, branch: Branch<String>, titles: Map<String, String>): List<List<Tab>> {
        val tabBars = mutableListOf<List<Tab>>()
        val firstTabBar = mutableListOf<Tab>()
        val remainingTabBars = mutableListOf<List<Tab>>()
        for (child in branch.children) {
            val selected = child.value == path[0]
            val name = child.firstLeafValue
            val title = titles.getValue(name)
            firstTabBar.add(Tab(name, title, selected))
            if (selected && child is Branch) {
                val childPath = path.subList(1, path.size)
                remainingTabBars.addAll(createTabBars(childPath, child, titles))
            }
        }
        tabBars.add(firstTabBar)
        tabBars.addAll(remainingTabBars)
        return tabBars
    }
}
