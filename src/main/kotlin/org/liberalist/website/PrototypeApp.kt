package org.liberalist.website

import java.nio.file.Paths

fun main(args: Array<String>) {
    val p = Paths.get("foo", "bar", "baz.txt")
    p.forEach {
        println(it.javaClass.toString() + " " + it.toString())
    }
}