package org.liberalist.website

class FakeFiles(val tree: List<FileOrDir>) : FilesNotImplemented() {
    interface FileOrDir
    data class File(val name: String, val content: String) : FileOrDir
    data class Dir(val name: String, val contents: List<FileOrDir>) : FileOrDir
}