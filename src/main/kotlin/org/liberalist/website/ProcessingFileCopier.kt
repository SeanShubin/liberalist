package org.liberalist.website

import java.nio.file.Path

class ProcessingFileCopier(val srcDir: Path, val destDir: Path, val fileProcessor: FileProcessor) : FileCopier {
    override fun copyFiles() {
        fileProcessor.processFiles(srcDir, destDir)
    }
}