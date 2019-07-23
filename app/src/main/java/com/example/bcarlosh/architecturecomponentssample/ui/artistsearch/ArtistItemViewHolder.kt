package com.example.bcarlosh.architecturecomponentssample.ui.artistsearch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.artist.Artist
import com.example.bcarlosh.architecturecomponentssample.helpers.GlideApp
import kotlinx.android.synthetic.main.item_artist.view.*
import java.text.DecimalFormat


class ArtistItemViewHolder(
    itemView: View,
    private val itemClick: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {


    fun bind(artist: Artist) {

        setImage(artist.image)
        setArtistTitle(artist.name)
        setListeners(artist.listeners)

        itemView.setOnClickListener {
            itemClick(artist.name)
        }
    }

    private fun setArtistTitle(artistName: String) {
        itemView.item_artist_title_textView.text = artistName
    }

    private fun setListeners(listeners: String) {
        val formatter = DecimalFormat("#,###,###")
        val formattedListeners = formatter.format(listeners.toInt())

        itemView.item_artist_listeners_value_textView.text = formattedListeners
    }

    private fun setImage(imageUrl: String) {
        GlideApp.with(itemView)
            .load(imageUrl)
            .placeholder(R.drawable.ic_cloud_off_48dp)
            .into(itemView.item_artist_imageView)
    }

}