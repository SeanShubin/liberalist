package org.liberalist.website

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet
import java.nio.file.Paths

fun main(args: Array<String>) {
    val baseDir = Paths.get(".")
    val contentDir = baseDir.resolve("content")
    val generatedHtmlDir = baseDir.resolve(Paths.get("build", "html"))

    val options = MutableDataSet()
    val parser = Parser.builder(options).build()
    val renderer = HtmlRenderer.builder(options).build()
    val document = parser.parse("This is *Sparta*")
    val html = renderer.render(document)
    println(html)

}
