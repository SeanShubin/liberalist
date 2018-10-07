package org.liberalist.website

import java.nio.file.Path

class LineEmittingNotifications(val emit: (String) -> Unit) : Notifications {
    override fun fileWrite(path: Path, text: String) {
        val bytes = text.length
        emit("$bytes characters written to file $path")
    }
}
