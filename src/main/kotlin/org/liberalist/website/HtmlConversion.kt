package org.liberalist.website

import java.nio.file.Path

data class HtmlConversion(val original: Path, val generated: Path, val name: String, val title: String)
