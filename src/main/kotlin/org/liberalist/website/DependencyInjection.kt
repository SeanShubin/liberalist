package org.liberalist.website

import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.liberalist.website.contract.FilesContract
import org.liberalist.website.contract.FilesDelegate
import org.liberalist.website.credentials.CredentialsProvider
import org.liberalist.website.s3.S3ApiClient
import org.liberalist.website.secrets.SecretsImpl
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths

class DependencyInjection(args: Array<String>) {
    private val secrets = SecretsImpl(
            awsAccessKeyId = args[0],
            awsSecretKey = args[1]
    )
    private val credentialsProvider =
            CredentialsProvider(secrets.awsAccessKeyId, secrets.awsSecretKey)
    private val baseDir: Path = Paths.get(".")
    private val sourceDir: Path = baseDir.resolve("content")
    private val generatedDir: Path = baseDir.resolve(Paths.get("build", "html"))
    private val sourceStaticDir: Path = baseDir.resolve("static")
    private val files: FilesContract = FilesDelegate
    private val charset: Charset = StandardCharsets.UTF_8
    private val markdownToHtmlConverter: MarkdownToHtmlConverter = FlexmarkConverter
    private val emitLine: (String) -> Unit = ::println
    private val staticContentCopier: StaticContentCopier = StaticContentCopierImpl(
            files, sourceStaticDir, generatedDir)
    private val contentScanner: ContentScanner = ContentScannerImpl(files, sourceDir)
    private val logger: Logger = LineEmittingLogger(emitLine)
    private val htmlGenerator: HtmlGenerator = HtmlGeneratorImpl(
            sourceDir, generatedDir, files, markdownToHtmlConverter, charset)
    private val modelFactory: ModelFactory = ModelFactoryImpl()
    private val modelGenerator: ModelGenerator = ModelGeneratorImpl(
            generatedDir,
            files,
            charset,
            modelFactory)
    private val bucketName = "liberalist.org"
    private val region = Regions.US_EAST_1
    private val s3Client = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(region).build()
    private val s3Api = S3ApiClient(s3Client, bucketName)
    private val s3Uploader: S3Uploader = S3UploaderImpl(s3Api, generatedDir, files)
    val deploySiteRunner: Runnable = DeploySite(
            contentScanner,
            logger::foundSources,
            htmlGenerator,
            logger::htmlConversionEvent,
            staticContentCopier,
            modelGenerator,
            s3Uploader)
}
