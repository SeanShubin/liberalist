package org.liberalist.website

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths

object DependencyInjection {
    private val baseDir: Path = Paths.get(".")
    private val sourceDir: Path = baseDir.resolve("content")
    private val generatedDir: Path = baseDir.resolve(Paths.get("build", "html"))
    private val filesContract: FilesContract = FilesDelegate
    private val charset: Charset = StandardCharsets.UTF_8
    private val markdownToHtmlConverter: MarkdownToHtmlConverter = FlexmarkConverter
    private val tabToHtmlConverter: TabToHtmlConverter = DefaultTabToHtmlConverter
    private val emitLine: (String) -> Unit = ::println
    private val notifications: Notifications = LineEmittingNotifications(emitLine)
    private val fileFilter: FileFilter = FileFilter(filesContract)
    private val htmlGenerator: () -> Unit = MarkdownHtmlGenerator(
            sourceDir,
            generatedDir,
            filesContract,
            charset,
            markdownToHtmlConverter,
            tabToHtmlConverter,
            notifications::fileWrite,
            fileFilter)
    private val fileCopier: () -> Unit = HtmlCssJsFileCopier(sourceDir, generatedDir, filesContract)
    val deploySiteRunner: Runnable = DeploySite(htmlGenerator, fileCopier)
}