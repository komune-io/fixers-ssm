package io.komune.c2.chaincode.api.fabric.extention

import java.io.File
import java.io.FileReader
import java.net.URISyntaxException
import java.net.URL

fun URL.asFileReader(): FileReader {
    val url = this.toString().removePrefix("file:")
    val folder = File(url)
    return if (folder.isDirectory()) {
        folder.listFiles()?.firstOrNull() ?: throw URISyntaxException(url, "No files found")
    } else {
        folder
    }.let {
        FileReader(it)
    }
}
