package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class StaticContentCopierImpl(private val files: FilesContract,
                              private val baseFromDir: Path,
                              private val baseToDir: Path) : StaticContentCopier {
    override fun copyStaticContent() {
        copyDir(baseFromDir, baseToDir)
    }

    private fun copyDir(fromDir: Path, toDir: Path) {
        val list = files.list(fromDir)
        list.forEach { file ->
            if (files.isDirectory(file)) {
                val newToDir = toDir.resolve(file.fileName)
                if (!files.exists(newToDir)) {
                    files.createDirectory(newToDir)
                }
                copyDir(file, newToDir)
            } else {
                val newFile = toDir.resolve(file.fileName)
                files.copy(file, newFile, StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }
}