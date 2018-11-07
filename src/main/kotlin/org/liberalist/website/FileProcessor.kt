package org.liberalist.website

import java.nio.file.Path

interface FileProcessor {
    fun processFiles(srcDir: Path, destDir: Path)
}