package com.example.bcarlosh.architecturecomponentssample.ui.artisttopalbum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.Album
import kotlinx.android.synthetic.main.artist_top_albums_fragment.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.loading_view.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf


class ArtistTopAlbumsFragment : Fragment() {


    private lateinit var viewModel: ArtistTopAlbumsViewModel
    private var safeArgs: ArtistTopAlbumsFragmentArgs? = null
    private lateinit var topAlbumsListAdapter: TopAlbumsListAdapter
    private val albumsList = mutableListOf<Album>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        safeArgs = arguments?.let {
            ArtistTopAlbumsFragmentArgs.fromBundle(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.artist_top_albums_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val artistName = safeArgs?.artistName ?: EMPTY_ARTIST_NAME
        viewModel = getViewModel { parametersOf(artistName) }

        updateSupportActionBarSubtitle(artistName)
        initRecyclerView()
        bindUI()
    }

    private fun updateSupportActionBarSubtitle(text: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = text
    }

    private fun bindUI() {
        showLoading()

        viewModel.topAlbumsListResponse.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            if (it.topalbums == null || it.topalbums.album.isEmpty()) {
                setErrorView(getString(R.string.no_albums_found))
            } else {
                albumsList.clear()
                albumsList.addAll(it.topalbums.album)
                updateRecyclerView()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            setErrorView(it)
        })
    }

    private fun initRecyclerView() {
        topAlbumsListAdapter = TopAlbumsListAdapter(albumsList) { artistName, albumName ->
            showAlbumDetail(artistName, albumName)
        }

        top_albums_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = topAlbumsListAdapter
        }
    }

    private fun updateRecyclerView() {
        topAlbumsListAdapter.notifyDataSetChanged()
        top_albums_recycler_view.visibility = View.VISIBLE
        hideLoading()
    }

    private fun showAlbumDetail(artistName: String, albumName: String) {
        val actionAlbumDetail =
            ArtistTopAlbumsFragmentDirections.actionArtistTopAlbumsFragmentToAlbumDetailActivity(artistName, albumName)
        Navigation.findNavController(top_albums_recycler_view).navigate(actionAlbumDetail)
    }

    private fun setErrorView(errorMsg: String) {
        top_albums_recycler_view.visibility = View.INVISIBLE
        hideLoading()

        top_albums_error_view.visibility = View.VISIBLE
        error_view_textView.text = errorMsg
    }

    private fun showLoading() {
        top_albums_error_view.visibility = View.GONE

        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        updateSupportActionBarSubtitle(EMPTY_ARTIST_NAME)
    }


    companion object {
        private const val EMPTY_ARTIST_NAME = ""
    }

}
