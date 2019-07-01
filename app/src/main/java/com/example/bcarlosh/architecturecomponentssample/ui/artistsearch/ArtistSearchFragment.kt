package com.example.bcarlosh.architecturecomponentssample.ui.artistsearch

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.artist.Artist
import com.example.bcarlosh.architecturecomponentssample.ui.MainActivity
import kotlinx.android.synthetic.main.artist_search_fragment.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.loading_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ArtistSearchFragment : Fragment() {


    private val viewModel: ArtistSearchViewModel by viewModel()
    private lateinit var artistListAdapter: ArtistListAdapter
    private val artistList = mutableListOf<Artist>()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.artist_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        bindUI()
    }

    private fun bindUI() {

        viewModel.artistSearchResponse.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            if (it.results.artistmatches.artist.isEmpty()) {
                setErrorView(getString(R.string.no_artists_on_search))
            } else {
                artistList.clear()
                artistList.addAll(it.results.artistmatches.artist)
                updateRecyclerView()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            setErrorView(it)
        })
    }

    private fun initRecyclerView() {
        artistListAdapter = ArtistListAdapter(artistList) { artistName ->
            showArtistTopAlbums(artistName)
        }

        artist_search_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = artistListAdapter
        }
    }

    private fun updateRecyclerView() {
        artistListAdapter.notifyDataSetChanged()
        artist_search_recycler_view.visibility = View.VISIBLE
        hideLoading()
    }

    private fun setErrorView(errorMsg: String) {
        artist_search_recycler_view.visibility = View.INVISIBLE
        hideLoading()

        artist_search_error_view.visibility = View.VISIBLE
        error_view_textView.text = errorMsg
    }

    private fun showLoading() {
        artist_search_error_view.visibility = View.GONE

        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    private fun showArtistTopAlbums(artistName: String) {
        val actionTopAlbums =
            ArtistSearchFragmentDirections.actionArtistSearchFragmentToArtistTopAlbumsFragment(artistName)
        Navigation.findNavController(artist_search_recycler_view).navigate(actionTopAlbums)
    }

    //region SearchView setup
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_view, menu)

        searchView = SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)

        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setHintTextColor(ContextCompat.getColor(context!!, R.color.primaryLightPlusColor))

        initSearchViewState()

        setOnCloseListener()
        setOnQueryTextListener()
    }

    private fun initSearchViewState() {
        if (viewModel.currentQueryValue.isEmpty()) {
            searchView.isIconified = false
        } else {
            searchView.isIconified = false
            searchView.setQuery(viewModel.currentQueryValue, false)
            searchView.clearFocus()
        }
    }

    private fun setOnCloseListener() {
        searchView.setOnCloseListener {
            onCloseSearchView()
            false
        }
    }

    private fun setOnQueryTextListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                onQueryTextSubmitted(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.currentQueryValue = newText
                return false
            }
        })
    }

    private fun onQueryTextSubmitted(artist: String) {
        if (artist.isNotEmpty()) {
            showLoading()
            searchView.clearFocus()
            viewModel.searchArtist(artist)
        }
    }

    private fun onCloseSearchView() {
        activity?.onBackPressed()
    }
    //endregion

}
