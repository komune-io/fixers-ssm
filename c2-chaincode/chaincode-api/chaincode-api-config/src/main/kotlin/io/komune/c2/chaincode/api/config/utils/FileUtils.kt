package io.komune.c2.chaincode.api.config.utils

import java.net.URL


object FileUtils {
    const val FILE: String = "file:"

    fun getUrl(path: String, resource: String): URL {
        val normalizedPath = if(!path.endsWith("/"))
            "$path/"
        else
            path
        val fullPath = normalizedPath + resource
        return getUrl(fullPath)
    }

    fun getUrl(resource: String): URL {
        if (resource.startsWith(FILE)) {
            return URL(resource)
        }
        return getResource(resource)
    }

    fun getResource(resourceName: String): URL {
        val url =  Thread.currentThread().contextClassLoader ?: FileUtils::class.java.getClassLoader()
        return url.getResource(resourceName)!!
    }
}
