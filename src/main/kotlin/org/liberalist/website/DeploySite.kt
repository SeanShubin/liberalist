package org.liberalist.website

class DeploySite(private val htmlGenerator: () -> Unit,
                 private val fileCopier: () -> Unit) : Runnable {
    override fun run() {
        htmlGenerator()
        fileCopier()
    }
}
