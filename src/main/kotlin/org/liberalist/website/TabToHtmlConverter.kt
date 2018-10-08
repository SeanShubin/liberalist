package org.liberalist.website

interface TabToHtmlConverter {
    fun tabToTopLevelHtml(tab: Tab): String
}
