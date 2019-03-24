package org.liberalist.website

interface TabToHtmlConverter {
    fun tabToTopLevelHtml(title: String, tab: Tab): String
}
