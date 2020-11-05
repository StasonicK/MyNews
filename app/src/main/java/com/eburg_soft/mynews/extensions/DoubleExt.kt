package com.eburg_soft.mynews.extensions

import timber.log.Timber
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP
import java.text.NumberFormat
import java.util.Locale

fun Double.round(number: Int,mode: RoundingMode): Double {
    val nf = NumberFormat.getNumberInstance(Locale.US)
    nf.apply {
        roundingMode = mode
        maximumFractionDigits = number
    }
    val result = nf.format(this).replace(",", "").toDouble()
    Timber.d("Rounded Double is %s", result)
    return result
}