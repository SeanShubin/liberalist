package org.liberalist.website

sealed class PageTree {
    data class File(val name: String, val title: String) : PageTree()
    data class Dir(val name: String, val children: List<PageTree>) : PageTree()
}