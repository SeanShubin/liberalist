package org.liberalist.website

data class Tab(val name: String, val subTabs: List<Tab> = emptyList())

fun main(args: Array<String>) {
    val allTabs = listOf(
            Tab("why", listOf(Tab("why"), Tab("about"), Tab("origin"))),
            Tab("principles", listOf(Tab("principles"))),
            Tab("discourse", listOf(Tab("discourse"), Tab("hijacked"), Tab("tyranny"), Tab("intelligence"), Tab("purity"))),
            Tab("details", listOf(Tab("details"), Tab("condorcet")))

    )
    println(allTabs)
}