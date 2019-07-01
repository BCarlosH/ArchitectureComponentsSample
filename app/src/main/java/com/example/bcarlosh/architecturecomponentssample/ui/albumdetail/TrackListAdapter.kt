package com.example.bcarlosh.architecturecomponentssample.ui.albumdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.track.Track


class TrackListAdapter(
    private val trackList: List<Track>
) : RecyclerView.Adapter<TrackItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return TrackItemViewHolder(view)
    }

    override fun getItemCount(): Int = trackList.size

    override fun onBindViewHolder(holder: TrackItemViewHolder, position: Int) {
        holder.bind(trackList[position], position)
    }

}