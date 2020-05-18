package dev.bmcreations.screen.library.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.api.load
import coil.extension.videoFrameMillis
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import dev.bmcreations.screen.library.R
import dev.bmcreations.screen.library.models.LibraryFile
import kotlinx.android.synthetic.main.library_file_item.view.*
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.seconds


class LibraryFileAdapter : ListAdapter<LibraryFile, LibraryFileVH>(LibraryFile.DIFFER) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryFileVH {
        return LibraryFileVH.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: LibraryFileVH, position: Int) {
        holder.entity = getItem(position)
    }
}

class LibraryFileVH private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @OptIn(ExperimentalTime::class)
    var entity: LibraryFile? = null
        set(value) {
            field = value?.also { file ->
                itemView.thumb.load(file.path, imageLoader = ImageLoader.Builder(itemView.context)
                    .componentRegistry {
                        add(VideoFrameFileFetcher(itemView.context))
                        add(VideoFrameUriFetcher(itemView.context))
                    }
                    .build()) {
                    videoFrameMillis(file.duration(itemView.context) / 2)

                }
                itemView.name.text = file.name
                with(file.duration(itemView.context)) {
                    itemView.length.text = String.format("%d:%02d",
                        TimeUnit.MILLISECONDS.toSeconds(this) / 60,
                        TimeUnit.MILLISECONDS.toSeconds(this) % 60)
                }
                itemView.size.text = file.size
            }
        }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): LibraryFileVH {
            return LibraryFileVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.library_file_item, parent, false)
            )
        }
    }
}
