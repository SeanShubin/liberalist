package org.liberalist.website

import org.liberalist.website.contract.FilesContract
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStream
import java.io.OutputStream
import java.nio.channels.SeekableByteChannel
import java.nio.charset.Charset
import java.nio.file.*
import java.nio.file.attribute.*
import java.util.function.BiPredicate
import java.util.stream.Stream

class MonitoredFiles(private val files: FilesContract,
                     private val emit:(String)->Unit):FilesContract {
    override fun newInputStream(path: Path, vararg options: OpenOption): InputStream {
        throw RuntimeException("not implemented")
    }

    override fun newOutputStream(path: Path, vararg options: OpenOption): OutputStream {
        throw RuntimeException("not implemented")
    }

    override fun newByteChannel(path: Path, options: Set<OpenOption>, vararg attrs: FileAttribute<*>): SeekableByteChannel {
        throw RuntimeException("not implemented")
    }

    override fun newByteChannel(path: Path, vararg options: OpenOption): SeekableByteChannel {
        throw RuntimeException("not implemented")
    }

    override fun newDirectoryStream(dir: Path): DirectoryStream<Path> {
        throw RuntimeException("not implemented")
    }

    override fun newDirectoryStream(dir: Path, glob: String): DirectoryStream<Path> {
        throw RuntimeException("not implemented")
    }

    override fun newDirectoryStream(dir: Path, filter: DirectoryStream.Filter<in Path>): DirectoryStream<Path> {
        throw RuntimeException("not implemented")
    }

    override fun createFile(path: Path, vararg attrs: FileAttribute<*>): Path {
        throw RuntimeException("not implemented")
    }

    override fun createDirectory(dir: Path, vararg attrs: FileAttribute<*>): Path {
        throw RuntimeException("not implemented")
    }

    override fun createDirectories(dir: Path, vararg attrs: FileAttribute<*>): Path {
        emit("creatDirectories $dir")
        return files.createDirectories(dir, *attrs)
    }

    override fun createTempFile(dir: Path, prefix: String, suffix: String, vararg attrs: FileAttribute<*>): Path {
        throw RuntimeException("not implemented")
    }

    override fun createTempFile(prefix: String, suffix: String, vararg attrs: FileAttribute<*>): Path {
        throw RuntimeException("not implemented")
    }

    override fun createTempDirectory(dir: Path, prefix: String, vararg attrs: FileAttribute<*>): Path {
        throw RuntimeException("not implemented")
    }

    override fun createTempDirectory(prefix: String, vararg attrs: FileAttribute<*>): Path {
        throw RuntimeException("not implemented")
    }

    override fun createSymbolicLink(link: Path, target: Path, vararg attrs: FileAttribute<*>): Path {
        throw RuntimeException("not implemented")
    }

    override fun createLink(link: Path, existing: Path): Path {
        throw RuntimeException("not implemented")
    }

    override fun delete(path: Path) {
        throw RuntimeException("not implemented")
    }

    override fun deleteIfExists(path: Path): Boolean {
        throw RuntimeException("not implemented")
    }

    override fun copy(source: Path, target: Path, vararg options: CopyOption): Path {
        emit("copying $source to $target")
        return files.copy(source, target, *options)
    }

    override fun move(source: Path, target: Path, vararg options: CopyOption): Path {
        throw RuntimeException("not implemented")
    }

    override fun readSymbolicLink(link: Path): Path {
        throw RuntimeException("not implemented")
    }

    override fun getFileStore(path: Path): FileStore {
        throw RuntimeException("not implemented")
    }

    override fun isSameFile(path: Path, path2: Path): Boolean {
        throw RuntimeException("not implemented")
    }

    override fun isHidden(path: Path): Boolean {
        throw RuntimeException("not implemented")
    }

    override fun probeContentType(path: Path): String {
        throw RuntimeException("not implemented")
    }

    override fun <V : FileAttributeView> getFileAttributeView(path: Path, type: Class<V>, vararg options: LinkOption): V? {
        throw RuntimeException("not implemented")
    }

    override fun <A : BasicFileAttributes> readAttributes(path: Path, type: Class<A>, vararg options: LinkOption): A {
        throw RuntimeException("not implemented")
    }

    override fun setAttribute(path: Path, attribute: String, value: Any, vararg options: LinkOption): Path {
        throw RuntimeException("not implemented")
    }

    override fun getAttribute(path: Path, attribute: String, vararg options: LinkOption): Any {
        throw RuntimeException("not implemented")
    }

    override fun readAttributes(path: Path, attributes: String, vararg options: LinkOption): Map<String, Any> {
        throw RuntimeException("not implemented")
    }

    override fun getPosixFilePermissions(path: Path, vararg options: LinkOption): Set<PosixFilePermission> {
        throw RuntimeException("not implemented")
    }

    override fun setPosixFilePermissions(path: Path, perms: Set<PosixFilePermission>): Path {
        throw RuntimeException("not implemented")
    }

    override fun getOwner(path: Path, vararg options: LinkOption): UserPrincipal {
        throw RuntimeException("not implemented")
    }

    override fun setOwner(path: Path, owner: UserPrincipal): Path {
        throw RuntimeException("not implemented")
    }

    override fun isSymbolicLink(path: Path): Boolean {
        throw RuntimeException("not implemented")
    }

    override fun isDirectory(path: Path, vararg options: LinkOption): Boolean {
        val result = files.isDirectory(path, *options)
        emit(if(result) "$path is a directory" else "$path is not a directory")
        return result
    }

    override fun isRegularFile(path: Path, vararg options: LinkOption): Boolean {
        throw RuntimeException("not implemented")
    }

    override fun getLastModifiedTime(path: Path, vararg options: LinkOption): FileTime {
        throw RuntimeException("not implemented")
    }

    override fun setLastModifiedTime(path: Path, time: FileTime): Path {
        throw RuntimeException("not implemented")
    }

    override fun size(path: Path): Long {
        throw RuntimeException("not implemented")
    }

    override fun exists(path: Path, vararg options: LinkOption): Boolean {
        val result = files.exists(path, *options)
        emit(if(result) "file $path exists" else "file $path does not exist")
        return result
    }

    override fun notExists(path: Path, vararg options: LinkOption): Boolean {
        throw RuntimeException("not implemented")
    }

    override fun isReadable(path: Path): Boolean {
        throw RuntimeException("not implemented")
    }

    override fun isWritable(path: Path): Boolean {
        throw RuntimeException("not implemented")
    }

    override fun isExecutable(path: Path): Boolean {
        throw RuntimeException("not implemented")
    }

    override fun walkFileTree(start: Path, options: Set<FileVisitOption>, maxDepth: Int, visitor: FileVisitor<in Path>): Path {
        throw RuntimeException("not implemented")
    }

    override fun walkFileTree(start: Path, visitor: FileVisitor<in Path>): Path {
        emit("walking file tree $start")
        return files.walkFileTree(start, visitor)
    }

    override fun newBufferedReader(path: Path, cs: Charset): BufferedReader {
        throw RuntimeException("not implemented")
    }

    override fun newBufferedReader(path: Path): BufferedReader {
        throw RuntimeException("not implemented")
    }

    override fun newBufferedWriter(path: Path, cs: Charset, vararg options: OpenOption): BufferedWriter {
        throw RuntimeException("not implemented")
    }

    override fun newBufferedWriter(path: Path, vararg options: OpenOption): BufferedWriter {
        throw RuntimeException("not implemented")
    }

    override fun copy(`in`: InputStream, target: Path, vararg options: CopyOption): Long {
        throw RuntimeException("not implemented")
    }

    override fun copy(source: Path, out: OutputStream): Long {
        throw RuntimeException("not implemented")
    }

    override fun readAllBytes(path: Path): ByteArray {
        throw RuntimeException("not implemented")
    }

    override fun readString(path: Path): String {
        throw RuntimeException("not implemented")
    }

    override fun readString(path: Path, cs: Charset): String {
        val result = files.readString(path, cs)
        emit("read ${result.length} characters from $path")
        return result
    }

    override fun readAllLines(path: Path, cs: Charset): List<String> {
        throw RuntimeException("not implemented")
    }

    override fun readAllLines(path: Path): List<String> {
        throw RuntimeException("not implemented")
    }

    override fun write(path: Path, bytes: ByteArray, vararg options: OpenOption): Path {
        throw RuntimeException("not implemented")
    }

    override fun write(path: Path, lines: Iterable<CharSequence>, cs: Charset, vararg options: OpenOption): Path {
        throw RuntimeException("not implemented")
    }

    override fun write(path: Path, lines: Iterable<CharSequence>, vararg options: OpenOption): Path {
        throw RuntimeException("not implemented")
    }

    override fun writeString(path: Path, csq: CharSequence, vararg options: OpenOption): Path {
        throw RuntimeException("not implemented")
    }

    override fun writeString(path: Path, csq: CharSequence, cs: Charset, vararg options: OpenOption): Path {
        emit("writing ${csq.length} characters to file $path")
        return files.writeString(path, csq, cs, *options)
    }

    override fun list(dir: Path): Stream<Path> {
        emit("listing $dir")
        return files.list(dir)
    }

    override fun walk(start: Path, vararg options: FileVisitOption): Stream<Path> {
        throw RuntimeException("not implemented")
    }

    override fun walk(start: Path, maxDepth: Int, vararg options: FileVisitOption): Stream<Path> {
        throw RuntimeException("not implemented")
    }

    override fun find(start: Path, maxDepth: Int, matcher: BiPredicate<Path, BasicFileAttributes>, vararg options: FileVisitOption): Stream<Path> {
        throw RuntimeException("not implemented")
    }

    override fun lines(path: Path, cs: Charset): Stream<String> {
        throw RuntimeException("not implemented")
    }

    override fun lines(path: Path): Stream<String> {
        throw RuntimeException("not implemented")
    }
}