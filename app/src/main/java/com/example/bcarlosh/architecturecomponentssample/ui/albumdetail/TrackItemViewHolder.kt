package com.example.bcarlosh.architecturecomponentssample.ui.albumdetail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bcarlosh.architecturecomponentssample.data.entity.track.Track
import kotlinx.android.synthetic.main.item_track.view.*
import java.lang.String.valueOf


class TrackItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {


    fun bind(track: Track, position: Int) {
        setTrackNumber(position)
        setTrackName(track.name)
        setTrackDuration(track.duration)
    }

    private fun setTrackNumber(number: Int) {
        val actualNumber = number + 1
        itemView.item_track_number_textView.text = valueOf(actualNumber)
    }

    private fun setTrackName(trackName: String) {
        itemView.item_track_name_value_textView.text = trackName
    }

    private fun setTrackDuration(trackDuration: String) {

        val minutes = trackDuration.toInt() / 60
        val seconds = trackDuration.toInt() % 60
        val formattedTrackDuration = String.format("%02d:%02d", minutes, seconds)

        itemView.item_track_duration_value_textView.text = formattedTrackDuration
    }

}