package org.liberalist.website.tree

sealed class Tree<T> {
    abstract val value: T
    abstract val firstLeafValue: T
    abstract fun leafPaths(from: List<T>): List<List<T>>
    abstract fun toLines(depth: Int): List<String>
    abstract fun <U> map(f: (T) -> U): Tree<U>
    fun toLines(): List<String> = toLines(0)

    companion object {
        fun indent(depth: Int): String = "  ".repeat(depth)

    }
}

class Leaf<T>(override val value: T) : Tree<T>() {
    override fun leafPaths(from: List<T>): List<List<T>> = listOf(from + value)
    override val firstLeafValue: T get() = value
    override fun toLines(depth: Int): List<String> = listOf(indent(depth) + value.toString())
    override fun <U> map(f: (T) -> U): Tree<U> = Leaf(f(value))
}

class Branch<T>(override val value: T, val children: List<Tree<T>>) : Tree<T>() {
    override fun leafPaths(from: List<T>): List<List<T>> {
        val newFrom = from + value
        return children.flatMap { it.leafPaths(newFrom) }
    }

    override val firstLeafValue: T
        get() = children[0].firstLeafValue

    override fun toLines(depth: Int): List<String> {
        val firstLine = listOf(indent(depth) + value.toString())
        val childLines = children.flatMap { it.toLines(depth + 1) }
        return firstLine + childLines
    }

    override fun <U> map(f: (T) -> U): Tree<U> = Branch(f(value), children.map { it.map(f) })
}
