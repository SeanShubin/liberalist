package org.liberalist.website

import org.liberalist.website.ModelGenerator.Page
import org.liberalist.website.ModelGenerator.Tab
import org.liberalist.website.tree.Branch
import org.liberalist.website.tree.Tree
import org.liberalist.website.tree.Tree.Companion.leafPaths

class ModelGeneratorImpl : ModelGenerator {
    override fun createModel(trees: List<Tree<String>>,
                             titles: Map<String, String>): Map<String, Page> {
        val paths = trees.leafPaths()
        fun createEntryWithTrees(path: List<String>) = createEntry(path, trees, titles)
        return paths.map(::createEntryWithTrees).toMap()
    }

    private fun createEntry(path: List<String>, trees: List<Tree<String>>, titles: Map<String, String>): Pair<String, Page> {
        val name = path[path.size - 1]
        val content = path.joinToString("/") + ".html"
        val tabBars = createTabBars(path, trees, titles)
        return Pair(name, Page(tabBars, content))
    }

    private fun createTabBars(path: List<String>, trees: List<Tree<String>>, titles: Map<String, String>): List<List<Tab>> {
        val tabBars = mutableListOf<List<Tab>>()
        val firstTabBar = mutableListOf<Tab>()
        val remainingTabBars = mutableListOf<List<Tab>>()
        for (tree in trees) {
            val selected = tree.value == path[0]
            val name = tree.firstLeafValue
            val title = titles.getValue(name)
            firstTabBar.add(Tab(name, title, selected))
            if (selected && tree is Branch) {
                val childPath = path.subList(1, path.size)
                remainingTabBars.addAll(createTabBars(childPath, tree.children, titles))
            }
        }
        tabBars.add(firstTabBar)
        tabBars.addAll(remainingTabBars)
        return tabBars
    }

}
