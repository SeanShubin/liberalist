package org.liberalist.website

interface MarkdownToHtmlConverter {
    fun markdownToHtml(markdown: String): String
}
