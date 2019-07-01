package com.example.bcarlosh.architecturecomponentssample.ui.storedalbumlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import kotlinx.android.synthetic.main.album_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class StoredAlbumListFragment : Fragment() {


    private val viewModel: StoredAlbumListViewModel by viewModel()
    private lateinit var storedAlbumsListAdapter: StoredAlbumsListAdapter
    private val storedAlbumsList = mutableListOf<AlbumInfo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        bindUI()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStoredAlbumsList()
    }

    private fun bindUI() {
        viewModel.storedAlbumsList.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            if (it.isNotEmpty()) {
                storedAlbumsList.clear()
                storedAlbumsList.addAll(it)

                initial_textView.visibility = View.GONE
                search_imageView.visibility = View.GONE
            } else {
                storedAlbumsList.clear()
                search_imageView.setOnClickListener { onSearchImageViewClick() }
                initial_textView.visibility = View.VISIBLE
                search_imageView.visibility = View.VISIBLE
            }

            updateRecyclerView()
        })
    }

    private fun initRecyclerView() {
        storedAlbumsListAdapter = StoredAlbumsListAdapter(storedAlbumsList) { artistName, albumName ->
            showAlbumDetail(artistName, albumName)
        }

        stored_albums_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = storedAlbumsListAdapter
        }
    }

    private fun updateRecyclerView() {
        storedAlbumsListAdapter.notifyDataSetChanged()
        stored_albums_recycler_view.visibility = View.VISIBLE
    }

    private fun onSearchImageViewClick() {
        showArtistSearchView()
    }

    //region SearchView setup
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                showArtistSearchView()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    //region Navigation
    private fun showArtistSearchView() {
        val action = StoredAlbumListFragmentDirections.actionAlbumListFragmentToArtistSearchFragment()
        Navigation.findNavController(stored_albums_recycler_view).navigate(action)
    }

    private fun showAlbumDetail(artistName: String, albumName: String) {
        val actionAlbumDetail =
            StoredAlbumListFragmentDirections.actionAlbumListFragmentToAlbumDetailActivity(artistName, albumName)
        Navigation.findNavController(stored_albums_recycler_view).navigate(actionAlbumDetail)
    }
    //endregion

}
