package com.davedecastro.yonduandroidexam.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

suspend fun isServerReachable() = withContext(Dispatchers.IO) {
    try {
        val connection = URL("http://ec2-52-76-75-52.ap-southeast-1.compute.amazonaws.com/")
            .openConnection()
        connection.connect()
        connection.getInputStream().close()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}