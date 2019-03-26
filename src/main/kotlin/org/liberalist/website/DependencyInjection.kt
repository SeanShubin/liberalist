package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import org.liberalist.website.contract.FilesDelegate
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths

object DependencyInjection {
    private val baseDir: Path = Paths.get(".")
    private val sourceDir: Path = baseDir.resolve("content")
    private val generatedDir: Path = baseDir.resolve(Paths.get("build", "html"))
    private val sourceStaticDir: Path = baseDir.resolve("static")
    private val files: FilesContract = FilesDelegate
    private val charset: Charset = StandardCharsets.UTF_8
    private val markdownToHtmlConverter: MarkdownToHtmlConverter = FlexmarkConverter
    private val tabToHtmlConverter: TabToHtmlConverter = DefaultTabToHtmlConverter
    private val emitLine: (String) -> Unit = ::println
    private val notifications: Notifications = LineEmittingNotifications(emitLine)
    private val fileFilter: FileFilter = FileFilter(files)
    //    private val htmlGenerator: () -> Unit = MarkdownHtmlGenerator(
//            sourceDir,
//            generatedDir,
//            files,
//            charset,
//            markdownToHtmlConverter,
//            tabToHtmlConverter,
//            notifications::fileWrite,
//            fileFilter)
    private val fileCopier: () -> Unit = HtmlCssJsFileCopier(sourceDir, generatedDir, files)
    private val staticContentCopier: StaticContentCopier = StaticContentCopierImpl(
            files, sourceStaticDir, generatedDir)
    private val contentScanner: ContentScanner = ContentScannerImpl(files, sourceDir)
    private val logger: Logger = LineEmittingLogger(emitLine)
    private val htmlGenerator: HtmlGenerator = HtmlGeneratorImpl(
            sourceDir, generatedDir, files, markdownToHtmlConverter, charset)
    private val modelFactory: ModelFactory = ModelFactoryImpl()
    private val modelGenerator: ModelGenerator = ModelGeneratorImpl(
            generatedDir,
            files,
            charset,
            modelFactory)
    val deploySiteRunner: Runnable = DeploySite(
            contentScanner,
            logger::foundSources,
            htmlGenerator,
            logger::htmlConversionEvent,
            staticContentCopier,
            modelGenerator)
}
