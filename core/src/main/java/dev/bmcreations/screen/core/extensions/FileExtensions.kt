package dev.bmcreations.screen.core.extensions

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import java.io.File

val File.size get() = if (!exists()) 0.0 else length().toDouble()
val File.sizeInKb get() = size / 1024
val File.sizeInMb get() = sizeInKb / 1024
val File.sizeInGb get() = sizeInMb / 1024
val File.sizeInTb get() = sizeInGb / 1024

fun File.sizeString(): String = size.toString()
fun File.sizeStringInKb(decimals: Int = 0): String = "%.${decimals}f".format(sizeInKb)
fun File.sizeStringInMb(decimals: Int = 0): String = "%.${decimals}f".format(sizeInMb)
fun File.sizeStringInGb(decimals: Int = 0): String = "%.${decimals}f".format(sizeInGb)

fun File.sizeStrWithBytes(): String = sizeString() + "b"
fun File.sizeStrWithKb(decimals: Int = 0): String = sizeStringInKb(decimals) + "Kb"
fun File.sizeStrWithMb(decimals: Int = 0): String = sizeStringInMb(decimals) + "Mb"
fun File.sizeStrWithGb(decimals: Int = 0): String = sizeStringInGb(decimals) + "Gb"
