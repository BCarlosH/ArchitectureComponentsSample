package com.example.bcarlosh.architecturecomponentssample.ui.storedalbumlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.example.bcarlosh.architecturecomponentssample.helpers.GlideApp
import kotlinx.android.synthetic.main.item_album.view.*
import java.text.DecimalFormat


class StoredAlbumItemViewHolder(
    itemView: View,
    private val itemClick: (String, String) -> Unit
) : RecyclerView.ViewHolder(itemView) {


    fun bind(album: AlbumInfo) {

        setImage(album.image)
        setAlbumTitle(album.name)
        setAlbumArtist(album.artist)
        setPlayCount(album.playcount)

        itemView.setOnClickListener {
            itemClick(album.artist, album.name)
        }
    }

    private fun setAlbumTitle(albumName: String) {
        itemView.item_album_title_textView.text = albumName
    }

    private fun setAlbumArtist(albumArtist: String) {
        itemView.item_album_artist_value_textView.visibility = View.VISIBLE
        itemView.item_album_artist_value_textView.text = albumArtist
    }

    private fun setPlayCount(playCount: String) {
        val formatter = DecimalFormat("#,###,###")
        val formattedPlayCount = formatter.format(playCount.toInt())

        itemView.item_album_playcount_value_textView.text = formattedPlayCount
    }

    private fun setImage(imageUrl: String) {
        GlideApp.with(itemView)
            .load(imageUrl)
            .placeholder(R.drawable.ic_album_48dp)
            .into(itemView.item_album_imageView)
    }

}