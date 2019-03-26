package org.liberalist.website.tree

import kotlin.test.Test
import kotlin.test.assertEquals

class TreeTest {
    @Test
    fun paths() {
        // given
        val branch = Branch("root", listOf(
                Leaf("a"),
                Branch("b", listOf(Leaf("c"), Leaf("d"))),
                Leaf("e")))
        val expected = listOf(
                listOf("a"),
                listOf("b", "c"),
                listOf("b", "d"),
                listOf("e"))

        // when
        val actual = branch.children.flatMap { it.leafPaths(emptyList()) }

        // then
        assertEquals(expected, actual)
    }

}