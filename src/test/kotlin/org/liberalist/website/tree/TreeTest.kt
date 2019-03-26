package org.liberalist.website.tree

import org.liberalist.website.tree.Tree.Companion.leafPaths
import kotlin.test.Test
import kotlin.test.assertEquals

class TreeTest {
    @Test
    fun paths() {
        // given
        val trees = listOf(
                Leaf("a"),
                Branch("b", listOf(Leaf("c"), Leaf("d"))),
                Leaf("e"))
        val expected = listOf(
                listOf("a"),
                listOf("b", "c"),
                listOf("b", "d"),
                listOf("e"))

        // when
        val actual = trees.leafPaths()

        // then
        assertEquals(expected, actual)
    }

}