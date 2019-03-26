package org.liberalist.website.tree

sealed class Tree<T> {
    abstract val value: T
    abstract val firstLeafValue: T
    abstract fun leafPaths(from: List<T>): List<List<T>>
    abstract fun toLines(depth: Int): List<String>

    companion object {
        fun <T> List<Tree<T>>.leafPaths(): List<List<T>> =
                this.flatMap { it.leafPaths(emptyList()) }

        fun <T> List<Tree<T>>.toLines(): List<String> =
                this.flatMap { it.toLines(0) }

        fun indent(depth: Int): String = "  ".repeat(depth)

    }
}

class Leaf<T>(override val value: T) : Tree<T>() {
    override fun leafPaths(from: List<T>): List<List<T>> = listOf(from + value)
    override val firstLeafValue: T
        get() = value

    override fun toLines(depth: Int): List<String> = listOf(indent(depth) + value.toString())
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
}
