package org.liberalist.website

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths

object DependencyInjection {
    private val baseDir: Path = Paths.get(".")
    private val sourceMarkdownDir: Path = baseDir.resolve("content")
    private val generatedHtmlDir: Path = baseDir.resolve(Paths.get("build", "html"))
    private val filesContract: FilesContract = FilesDelegate
    private val charset: Charset = StandardCharsets.UTF_8
    private val markdownToHtmlConverter: MarkdownToHtmlConverter = FlexmarkConverter
    private val tabToHtmlConverter: TabToHtmlConverter = DefaultTabToHtmlConverter
    private val emitLine: (String) -> Unit = ::println
    private val notifications: Notifications = LineEmittingNotifications(emitLine)
    private val htmlGenerator: HtmlGenerator = MarkdownHtmlGenerator(
            sourceMarkdownDir,
            generatedHtmlDir,
            filesContract,
            charset,
            markdownToHtmlConverter,
            tabToHtmlConverter,
            notifications::fileWrite)
    val deploySiteRunner: Runnable = DeploySite(htmlGenerator)
}