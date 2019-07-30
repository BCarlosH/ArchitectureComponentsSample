package com.example.bcarlosh.architecturecomponentssample.ui.albumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.track.Track
import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallStatus
import kotlinx.android.synthetic.main.album_detail_fragment.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.loading_view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AlbumDetailFragment : Fragment() {


    private val viewModel: AlbumDetailViewModel by sharedViewModel()
    private lateinit var trackListAdapter: TrackListAdapter
    private val tracksList = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        bindUI()
    }

    private fun bindUI() {

        viewModel.albumInfoResponse.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            when (it) {
                is CallStatus.Loading -> showLoading()
                is CallStatus.Success -> loadingSuccess(it.data)
                is CallStatus.Error -> setErrorView(it.throwable.message)
            }
        })
    }

    private fun initRecyclerView() {
        trackListAdapter = TrackListAdapter(tracksList)

        album_detail_tracks_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = trackListAdapter
        }
    }

    private fun updateRecyclerView() {
        trackListAdapter.notifyDataSetChanged()
        album_detail_tracks_recycler_view.visibility = View.VISIBLE
        hideLoading()
    }

    private fun loadingSuccess(albumInfoResponse: AlbumInfoResponse) {
        if (albumInfoResponse.album == null || albumInfoResponse.album.tracks.track.isEmpty()) {
            setErrorView(getString(R.string.no_album_detail_found))
        } else {
            tracksList.clear()
            tracksList.addAll(albumInfoResponse.album.tracks.track)
            updateRecyclerView()
            viewModel.currentAlbum = albumInfoResponse.album
        }
    }

    private fun setErrorView(errorMsg: String?) {
        album_detail_tracks_recycler_view.visibility = View.INVISIBLE
        hideLoading()

        album_detail_error_view.visibility = View.VISIBLE

        if (errorMsg != null) {
            error_view_textView.text = errorMsg
        } else {
            error_view_textView.text = getString(R.string.generic_error)
        }
    }

    private fun showLoading() {
        album_detail_error_view.visibility = View.GONE

        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

}
