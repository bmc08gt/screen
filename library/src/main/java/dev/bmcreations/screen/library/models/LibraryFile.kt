package dev.bmcreations.screen.library.models

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.io.File

data class LibraryFile(
    val name: String,
    val path: String,
    val size: String
) {

    fun duration(context: Context): Long {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, Uri.fromFile(File(path)))
        val time: String? = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val timeInMillis = time?.toLong() ?: 0

        retriever.release()

        return timeInMillis
    }

    companion object {
        val DIFFER = object : DiffUtil.ItemCallback<LibraryFile>() {
            override fun areItemsTheSame(oldItem: LibraryFile, newItem: LibraryFile): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: LibraryFile, newItem: LibraryFile): Boolean {
                return oldItem == newItem
            }

        }
    }
}
