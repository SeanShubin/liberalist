package org.liberalist.website.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import java.nio.file.Path

class S3ApiClient(private val client: AmazonS3,
                  private val bucketName: String,
                  private val emit:(String) -> Unit) : S3Api {
    override fun ensureBucketExists() {
        val buckets = client.listBuckets()
        buckets.map{it.name}.forEach{
            emit("found bucket $it")
        }
        if (!buckets.any { bucket -> bucket.name == bucketName }) {
            emit("creating bucket: $bucketName")
            client.createBucket(bucketName)
        }
    }

    override fun uploadFile(name: String, path: Path) {
        val extension = getExtension(name)
        val contentType = contentTypes.getValue(extension)
        emit("uploading file to bucket $bucketName with contentType $contentType: $name -> $path")
        val metadata = ObjectMetadata()
        metadata.contentType = contentType
        val request = PutObjectRequest(
                bucketName,
                name,
                path.toFile()).
                withCannedAcl(CannedAccessControlList.PublicRead).
                withMetadata(metadata)
        client.putObject(request)
    }

    override fun enableWebsiteHosting() {
        val configuration = BucketWebsiteConfiguration("index.html", "index.html")
        client.setBucketWebsiteConfiguration(bucketName, configuration)
    }

    private fun getExtension(name:String):String {
        val dotIndex = name.lastIndexOf('.')
        return name.substring(dotIndex+1)
    }
    companion object {
        private val contentTypes = mapOf(
                Pair("json", "text/json; charset=UTF-8"),
                Pair("html", "text/html; charset=UTF-8"),
                Pair("css", "text/css; charset=UTF-8"),
                Pair("png", "image/png"),
                Pair("js", "application/javascript; charset=UTF-8"),
                Pair("md", "text/markdown"))
    }
}
