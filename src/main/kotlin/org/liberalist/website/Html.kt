package org.liberalist.website

object Html {
    fun ul(content: List<String>): List<String> = elementWithContent("ul", content)
    fun li(content: List<String>): List<String> = elementWithContent("li", content)
    fun anchor(name: String, href: String): List<String> = elementWithAttributesAndContent(
            "a",
            listOf(Pair("href", href)),
            listOf(name))

    private fun elementWithContent(tag: String, content: List<String>): List<String> {
        return when (content.size) {
            0 -> listOf("""<$tag></$tag>""")
            1 -> {
                val contentLine = content[0]
                listOf("""<$tag>$contentLine</$tag>""")
            }
            else -> {
                listOf("<$tag>") + content.map(::indent) + listOf("</$tag>")
            }
        }
    }

    private fun elementWithAttributesAndContent(
            tag: String,
            attributes: List<Pair<String, String>>,
            content: List<String>): List<String> {
        val attributesString = composeAttributes(attributes)
        return when (content.size) {
            0 -> listOf("""<$tag$attributesString></$tag>""")
            1 -> {
                val contentLine = content[0]
                listOf("""<$tag$attributesString>$contentLine</$tag>""")
            }
            else -> {
                listOf("<$tag$attributesString>") + content.map(::indent) + listOf("</$tag>")
            }
        }
    }

    private fun composeAttributes(attributes: List<Pair<String, String>>): String = when (attributes.size) {
        0 -> ""
        1 -> " " + composeAttribute(attributes[0])
        else -> " " + attributes.joinToString(" ", transform = ::composeAttribute)
    }

    private fun composeAttribute(pair: Pair<String, String>): String {
        val (name, value) = pair
        return composeAttribute(name, value)
    }

    private fun composeAttribute(name: String, value: String): String =
            """$name="$value""""

    private fun indent(s: String): String = "  $s"
}
