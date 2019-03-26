package org.liberalist.website

import java.nio.file.Path

sealed class HtmlConversion {
    abstract val original: Path
    abstract val name: String
}

data class HtmlConversionFile(
        override val original: Path,
        val generated: Path,
        override val name: String,
        val title: String) : HtmlConversion()

data class HtmlConversionDir(override val original: Path,
                             override val name: String) : HtmlConversion()
