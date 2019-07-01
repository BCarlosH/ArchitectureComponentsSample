package com.example.bcarlosh.architecturecomponentssample.ui.storedalbumlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo


class StoredAlbumsListAdapter(
    private val topAlbumsList: List<AlbumInfo>,
    private val itemClick: (String, String) -> Unit
) : RecyclerView.Adapter<StoredAlbumItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoredAlbumItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return StoredAlbumItemViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int = topAlbumsList.size

    override fun onBindViewHolder(holder: StoredAlbumItemViewHolder, position: Int) {
        holder.bind(topAlbumsList[position])
    }

}