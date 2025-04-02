package com.example.assaply.presentation.common

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatPublishedAt(dateString: String?): String {
    return try {
        if (dateString == null) return "—"
        val utcZoned = ZonedDateTime.parse(dateString)
        val moscowTime = utcZoned.withZoneSameInstant(ZoneId.of("Europe/Moscow"))
        val formatter = DateTimeFormatter.ofPattern("d MMMM, HH:mm", java.util.Locale("ru"))
        formatter.format(moscowTime)
    } catch (e: Exception) {
        "—"
    }
}
