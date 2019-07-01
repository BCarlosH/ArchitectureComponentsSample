package com.example.bcarlosh.architecturecomponentssample.ui.artisttopalbum

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.Image
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.Album
import com.example.bcarlosh.architecturecomponentssample.helpers.GlideApp
import kotlinx.android.synthetic.main.item_album.view.*
import java.text.DecimalFormat


class AlbumItemViewHolder(
    itemView: View,
    private val itemClick: (String, String) -> Unit
) : RecyclerView.ViewHolder(itemView) {


    fun bind(album: Album) {

        setImage(getImageUrl(album.image))
        setAlbumTitle(album.name)
        setPlayCount(album.playcount)

        itemView.setOnClickListener {
            itemClick(album.artist.name, album.name)
        }
    }

    private fun setAlbumTitle(albumName: String) {
        itemView.item_album_title_textView.text = albumName
    }

    private fun setPlayCount(playCount: Int) {
        val formatter = DecimalFormat("#,###,###")
        val formattedPlayCount = formatter.format(playCount)

        itemView.item_album_playcount_value_textView.text = formattedPlayCount
    }

    private fun setImage(imageUrl: String) {
        GlideApp.with(itemView)
            .load(imageUrl)
            .placeholder(R.drawable.ic_album_48dp)
            .into(itemView.item_album_imageView)
    }

    private fun getImageUrl(imageList: List<Image>): String {
        return when {
            imageList.size >= 3 -> imageList[2].text
            imageList.size == 2 -> imageList[1].text
            imageList.size == 1 -> imageList[0].text
            else -> ""
        }
    }

}