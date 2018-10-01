package org.liberalist.website

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet

object FlexmarkConverter : Converter {
    private val options = MutableDataSet()
    private val parser = Parser.builder(options).build()
    private val renderer = HtmlRenderer.builder(options).build()
    override fun markdownToHtml(markdown: String): String {
        val document = parser.parse(markdown)
        val html = renderer.render(document)
        return html
    }
}
