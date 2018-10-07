package org.liberalist.website

interface Converter {
    fun markdownToHtml(markdown: String): String
}