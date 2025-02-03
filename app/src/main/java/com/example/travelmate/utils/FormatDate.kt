package com.example.travelmate.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.convertDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("EEE, dd MM yyyy", Locale.ENGLISH)
        val date = inputFormat.parse(this)
        date?.let { outputFormat.format(it) } ?: "Invalid Date"
    } catch (e: Exception) {
        "Invalid Date"
    }
}