package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import org.liberalist.website.s3.S3Api
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

class S3UploaderImpl(private val s3Api: S3Api,
                     private val generated: Path,
                     private val files: FilesContract) : S3Uploader {
    override fun uploadToS3() {
        s3Api.ensureBucketExists()
        val visitor = object : FileVisitor<Path> {
            override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult =
                    FileVisitResult.CONTINUE

            override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                uploadFile(file!!)
                return FileVisitResult.CONTINUE
            }

            override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult =
                    FileVisitResult.CONTINUE

            override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult =
                    FileVisitResult.CONTINUE
        }
        files.walkFileTree(generated, visitor)
    }

    private fun uploadFile(file: Path) {
        val name = generated.relativize(file).toString()
        s3Api.uploadFile(name, file)
    }
}
