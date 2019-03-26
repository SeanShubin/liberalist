package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import org.liberalist.website.contract.FilesDelegate
import org.liberalist.website.tree.Tree.Companion.toLines
import java.nio.file.Paths

fun main(args: Array<String>) {
    val files: FilesContract = FilesDelegate
    val basePath = Paths.get("content")
    val contentScanner: ContentScanner = ContentScannerImpl(files, basePath)
    val sources = contentScanner.findSources()
    sources.toLines().forEach(::println)
}