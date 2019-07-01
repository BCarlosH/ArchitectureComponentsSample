package com.example.bcarlosh.architecturecomponentssample.ui.albumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.Image
import com.example.bcarlosh.architecturecomponentssample.data.entity.track.Track
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

        updateSupportActionBarTitle(viewModel.currentAlbumName)
        initRecyclerView()
        bindUI()
    }

    private fun updateSupportActionBarTitle(text: String) {
        (activity as? AlbumDetailActivity)?.setCollapsingToolbarTitle(text)
    }

    private fun bindUI() {
        showLoading()

        viewModel.albumInfoResponse.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            if (it.album == null || it.album.tracks.track.isEmpty()) {
                setErrorView(getString(R.string.no_album_detail_found))
            } else {
                setAlbumImage(getImageUrl(it.album.image))
                (activity as? AlbumDetailActivity)?.showStoreAlbumFab()
                tracksList.clear()
                tracksList.addAll(it.album.tracks.track)
                updateRecyclerView()
                viewModel.currentAlbum = it.album
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            setErrorView(it)
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

    private fun setAlbumImage(imageUrl: String) {
        (activity as? AlbumDetailActivity)?.setAlbumImage(imageUrl)
    }

    private fun getImageUrl(imageList: List<Image>): String {
        return when {
            imageList.size >= 4 -> imageList[3].text
            imageList.size == 3 -> imageList[2].text
            imageList.size == 2 -> imageList[1].text
            imageList.size == 1 -> imageList[0].text
            else -> ""
        }
    }

    private fun setErrorView(errorMsg: String) {
        album_detail_tracks_recycler_view.visibility = View.INVISIBLE
        hideLoading()

        (activity as? AlbumDetailActivity)?.apply {
            hideStoreAlbumFab()
            setErrorAlbumImage()
            setAppBarExpanded(false)
        }

        album_detail_error_view.visibility = View.VISIBLE
        error_view_textView.text = errorMsg
    }

    private fun showLoading() {
        album_detail_error_view.visibility = View.GONE

        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

}
