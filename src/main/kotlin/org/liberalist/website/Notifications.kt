package org.liberalist.website

import java.nio.file.Path

interface Notifications {
    fun fileWrite(path: Path, text:String)
}
