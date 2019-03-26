package org.liberalist.website

import com.vladsch.flexmark.ast.Heading
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet

object FlexmarkConverter : MarkdownToHtmlConverter {
    private val options = MutableDataSet()
    private val parser = Parser.builder(options).build()
    private val renderer = HtmlRenderer.builder(options).build()
    override fun markdownToHtml(markdown: String): TitleAndHtml {
        val document = parser.parse(markdown)
        val title = (document.firstChild as Heading).text.toString()
        val html = renderer.render(document)
        return TitleAndHtml(title, html)
    }
}
