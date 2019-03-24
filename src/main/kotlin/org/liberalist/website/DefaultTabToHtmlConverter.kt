package org.liberalist.website

import org.liberalist.website.Html.anchor
import org.liberalist.website.Html.li
import org.liberalist.website.Html.ul

object DefaultTabToHtmlConverter : TabToHtmlConverter {
    override fun tabToTopLevelHtml(title: String, tab: Tab): String {
        val text = tabToHtml(tab)
        val result = htmlWrapper(title, text)
        return result
    }

    private fun htmlWrapper(title: String, text: String): String {
        val result =
                """<!DOCTYPE html>
                 |<html lang="en">
                 |<head>
                 |    <meta charset="UTF-8">
                 |    <name>$title</name>
                 |</head>
                 |<body>
                 |$text
                 |</body>
                 |</html>""".trimMargin()
        return result
    }

    private fun tabToHtml(tab: Tab): String {
        val lines = tabToHtmlRecursive(tab)
        val result = lines.joinToString("\n")
        return result
    }

    private fun tabToHtmlRecursive(tab: Tab): List<String> {
        val result = ul(subTabsToHtml(tab.subTabs))
        return result
    }

    private fun subTabsToHtml(subTabs: List<Tab>): List<String> {
        val result = subTabs.flatMap(::subTabToHtml)
        return result
    }

    private fun subTabToHtml(subTab: Tab): List<String> {
        val result = if (subTab.subTabs.isEmpty()) {
            li(anchor(subTab.name, subTab.href))
        } else {
            val header = anchor(subTab.name, subTab.href)
            val childContent = tabToHtmlRecursive(subTab)
            val content = header + childContent
            li(content)
        }
        return result
    }
}
