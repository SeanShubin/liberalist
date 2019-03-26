package org.liberalist.website

import java.nio.charset.Charset
import java.nio.file.FileVisitor
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import kotlin.test.Test


data class Tab(val name: String, val active: Boolean)
data class TabBar(val tabs: List<Tab>)
data class Page(val tabBars: List<TabBar>, val contentPath: String)
class JsonModelGeneratorTest {
    @Test
    fun generateModelForWeb() {
        // given
        val fileContentMap = mapOf(
                Pair("01-a.md", "a markdown"),
                Pair("02-b/01-c.md", "c markdown"),
                Pair("02-b/02-d.md", "d markdown"),
                Pair("03-e.md", "e markdown"))
        val markdownTitleMap = mapOf(
                Pair("a markdown", "a title"),
                Pair("c markdown", "c title"),
                Pair("d markdown", "d title"),
                Pair("e markdown", "e title")
        )
        val dummyAttrs: BasicFileAttributes? = null
        val files = object : FilesNotImplemented() {
            override fun walkFileTree(start: Path, visitor: FileVisitor<in Path>): Path {
                fun visitFile(name: String) {
                    val path = Paths.get(name)
                    visitor.visitFile(path, dummyAttrs)
                }
                fileContentMap.keys.forEach(::visitFile)
                return start
            }

            override fun readString(path: Path, cs: Charset): String {
                return fileContentMap.getValue(path.toString())
            }
        }
        val markdownToHtmlTransformer = object : MarkdownToHtmlTransformer {
            override fun transform(file: Path): String {
                val markdown = fileContentMap.getValue(file.toString())
                val title = markdownTitleMap.getValue(markdown)
                return title
            }
        }

//        val modelText = loadResource("/sample-builder.json")
//        val siteGenerator = SiteGenerator()
//
//        // when
//        siteGenerator.generate()
//
//        // then
//        files.checkFile("builder.json", modelText)
//        files.checkFile("content/a.html", "<p>a content</p>")
//        files.checkFile("content/b/c.html", "<p>c content</p>")
//        files.checkFile("content/b/d.html", "<p>d content</p>")
//        files.checkFile("content/e.html", "<p>e content</p>")
    }
}