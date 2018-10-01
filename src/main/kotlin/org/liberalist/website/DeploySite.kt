package org.liberalist.website

class DeploySite(private val htmlGenerator:HtmlGenerator):Runnable  {
    override fun run() {
        htmlGenerator.generateHtml()
    }
}
