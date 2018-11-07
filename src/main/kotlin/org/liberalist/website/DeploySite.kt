package org.liberalist.website

class DeploySite(private val htmlGenerator: HtmlGenerator,
                 private val fileCopier: FileCopier) : Runnable {
    override fun run() {
        htmlGenerator.generateHtml()
        fileCopier.copyFiles()
    }
}
