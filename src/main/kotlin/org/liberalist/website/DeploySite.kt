package org.liberalist.website

import org.liberalist.website.tree.Tree
import java.nio.file.Path

class DeploySite(private val contentScanner: ContentScanner,
                 private val htmlGenerator: HtmlGenerator,
                 private val staticContentCopier: StaticContentCopier,
                 private val modelGenerator: ModelGenerator,
                 private val s3Uploader: S3Uploader) : Runnable {
    override fun run() {
        val sources = contentScanner.findSources()
        val htmlGeneratorResult = htmlGenerator.generateHtml(sources)
        staticContentCopier.copyStaticContent()
        modelGenerator.generateModel(htmlGeneratorResult)
        s3Uploader.uploadToS3()
    }
}
