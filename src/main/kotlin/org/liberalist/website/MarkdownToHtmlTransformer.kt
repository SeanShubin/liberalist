package org.liberalist.website

import java.nio.file.Path

interface MarkdownToHtmlTransformer {
    fun transform(file: Path): String
}
