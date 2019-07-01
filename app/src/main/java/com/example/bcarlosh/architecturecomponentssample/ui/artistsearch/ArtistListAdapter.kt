package com.example.bcarlosh.architecturecomponentssample.ui.artistsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.artist.Artist


class ArtistListAdapter(
    private val artistList: List<Artist>,
    private val itemClick: (String) -> Unit
) : RecyclerView.Adapter<ArtistItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return ArtistItemViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int = artistList.size

    override fun onBindViewHolder(holder: ArtistItemViewHolder, position: Int) {
        holder.bind(artistList[position])
    }

}