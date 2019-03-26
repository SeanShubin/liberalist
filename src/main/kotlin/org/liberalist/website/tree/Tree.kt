package org.liberalist.website.tree

sealed class Tree<T> {
    abstract val value: T
    abstract val firstLeafValue: T
    abstract fun leafPaths(from: List<T>): List<List<T>>

    companion object {
        fun <T> List<Tree<T>>.leafPaths(): List<List<T>> =
                this.flatMap { it.leafPaths(emptyList()) }

    }
}

class Leaf<T>(override val value: T) : Tree<T>() {
    override fun leafPaths(from: List<T>): List<List<T>> = listOf(from + value)
    override val firstLeafValue: T
        get() = value
}

class Branch<T>(override val value: T, val children: List<Tree<T>>) : Tree<T>() {
    override fun leafPaths(from: List<T>): List<List<T>> {
        val newFrom = from + value
        return children.flatMap { it.leafPaths(newFrom) }
    }

    override val firstLeafValue: T
        get() = children[0].firstLeafValue
}
