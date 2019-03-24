package org.liberalist.website

import com.vladsch.flexmark.ast.Heading
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet
import org.liberalist.website.compare.ListDifference
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FlexmarkTest {
    @Test
    fun getHtmlAndTitle() {
        // given
        val markdown =
                """# Subject
                   |some text
                   |
                   |## My List
                   |- Item 1
                   |- Item 2
                   |- Item 3
                """.trimMargin()
        val options = MutableDataSet()
        val parser = Parser.builder(options).build()
        val renderer = HtmlRenderer.builder(options).build()
        val expectedHtml =
                """<h1>Subject</h1>
                  |<p>some text</p>
                  |<h2>My List</h2>
                  |<ul>
                  |<li>Item 1</li>
                  |<li>Item 2</li>
                  |<li>Item 3</li>
                  |</ul>
                  |""".trimMargin()

        // when
        val document = parser.parse(markdown)
        val actualHtml = renderer.render(document)

        // then
        val children = document.childIterator.toList()
        val h1 = children[0] as Heading
        assertEquals(h1.text.toString(), "Subject")

        assertMultilineEquals(expectedHtml, actualHtml)
    }

    private fun <T> Iterator<T>.toList(): List<T> {
        val list = mutableListOf<T>()
        while (this.hasNext()) {
            list.add(this.next())
        }
        return list
    }

    private fun assertMultilineEquals(expected: String, actual: String) {
        val difference = ListDifference.compare(
                "expected", expected,
                "actual  ", actual)
        assertTrue(difference.isSame, difference.messageLines.joinToString("\n"))
    }
}
