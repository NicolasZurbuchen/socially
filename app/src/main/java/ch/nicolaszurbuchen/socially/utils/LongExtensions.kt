package ch.nicolaszurbuchen.socially.utils

import java.text.DateFormat
import java.util.Date
import java.util.Locale

fun Long.toReadableDate(): String {
    return DateFormat.getDateTimeInstance(
        DateFormat.LONG, DateFormat.SHORT, Locale.getDefault()
    ).format(Date(this))
}