package org.liberalist.website

object DefaultTabToHtmlConverter : TabToHtmlConverter {
    override fun tabToTopLevelHtml(tab: Tab): String = htmlWrapper(tab.name, tabToHtml(tab))
    private fun htmlWrapper(title: String, text: String): String {
        return """<!DOCTYPE html>
                 |<html lang="en">
                 |<head>
                 |    <meta charset="UTF-8">
                 |    <title>$title</title>
                 |</head>
                 |<body>
                 |$text
                 |</body>
                 |</html>""".trimMargin()
    }

    private fun tabToHtml(tab: Tab): String {
        return tabToHtmlRecursive(tab).joinToString("\n")
    }

    private fun tabToHtmlRecursive(tab: Tab): List<String> {
        return if (tab.subTabs.isEmpty()) {
            listOf("<ul>${tab.name}</ul>")
        } else {
            listOf("<ul>") + subTabsToHtml(tab.subTabs).map(::indent) + listOf("</ul>")
        }
    }

    private fun subTabsToHtml(subTabs: List<Tab>): List<String> {
        return subTabs.flatMap(::subTabToHtml)
    }

    private fun subTabToHtml(subTab: Tab): List<String> {
        return if (subTab.subTabs.isEmpty()) {
            listOf("<li>${subTab.name}</li>")

        } else {
            listOf("<li>${subTab.name}") +
                    tabToHtmlRecursive(subTab).map(::indent) +
                    listOf("</li>")

        }
    }

    private fun indent(line: String): String = "    $line"
}
