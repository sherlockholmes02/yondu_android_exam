package com.davedecastro.yonduandroidexam.utils

import java.text.SimpleDateFormat
import java.util.*

private val readableDateFormat = SimpleDateFormat("MMMM d, YYYY", Locale.US)
private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun Date.toReadableDateString(): String =
    try {
        readableDateFormat.format(this)
    } catch (e: Exception) {
        ""
    }

fun String.toDate(): Date? =
    try {
        readableDateFormat.parse(this)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        try {
            dateFormat.parse(this)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }
