package com.example.bcarlosh.architecturecomponentssample.ui.artisttopalbum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.Album


class TopAlbumsListAdapter(
    private val topAlbumsList: List<Album>,
    private val itemClick: (String, String) -> Unit
) : RecyclerView.Adapter<AlbumItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumItemViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int = topAlbumsList.size

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        holder.bind(topAlbumsList[position])
    }

}