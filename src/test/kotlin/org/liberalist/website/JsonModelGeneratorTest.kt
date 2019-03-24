package org.liberalist.website

import kotlin.test.Test


data class Tab(val name: String, val active: Boolean)
data class TabBar(val tabs: List<Tab>)
data class Page(val tabBars: List<TabBar>, val contentPath: String)

class JsonModelGeneratorTest {
    @Test
    fun generateModelForWeb() {
        // given
        val files = FakeFiles(
                listOf(FakeFiles.File("01-a.md", "# A Title\na content"),
                        FakeFiles.Dir("02-b", listOf(
                                FakeFiles.File("01-c.md", "# C Title\nc content"),
                                FakeFiles.File("02-d.md", "# D Title\nd content"))),
                        FakeFiles.File("03-e.md", "# E Title\ne content")))
        val expected = mapOf(
                Pair("a", Page(
                        tabBars = listOf(TabBar(listOf(
                                Tab("A Title", active = true),
                                Tab("C Title", active = false),
                                Tab("E Title", active = false)))),
                        contentPath = "/a.html")),
                Pair("c", Page(
                        tabBars = listOf(
                                TabBar(listOf(
                                        Tab("A Title", active = false),
                                        Tab("C Title", active = true),
                                        Tab("E Title", active = false))),
                                TabBar(listOf(
                                        Tab("C Title", active = true),
                                        Tab("D Title", active = false)))),
                        contentPath = "/b/c.html")),
                Pair("d", Page(
                        tabBars = listOf(
                                TabBar(listOf(
                                        Tab("A Title", active = false),
                                        Tab("C Title", active = true),
                                        Tab("E Title", active = false))),
                                TabBar(listOf(
                                        Tab("C Title", active = false),
                                        Tab("D Title", active = true)))),
                        contentPath = "/b/d.html")),
                Pair("e", Page(
                        tabBars = listOf(TabBar(listOf(
                                Tab("A Title", active = false),
                                Tab("C Title", active = false),
                                Tab("E Title", active = true)))),
                        contentPath = "/e.html")))

        // when

    }
}