package com.example.bcarlosh.architecturecomponentssample.di

import com.example.bcarlosh.architecturecomponentssample.ui.albumdetail.AlbumDetailViewModel
import com.example.bcarlosh.architecturecomponentssample.ui.artistsearch.ArtistSearchViewModel
import com.example.bcarlosh.architecturecomponentssample.ui.artisttopalbum.ArtistTopAlbumsViewModel
import com.example.bcarlosh.architecturecomponentssample.ui.storedalbumlist.StoredAlbumListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelFactoryModule = module {

    viewModel { StoredAlbumListViewModel(get()) }

    viewModel { ArtistSearchViewModel(get(), get()) }

    viewModel { (artistName: String) -> ArtistTopAlbumsViewModel(artistName, get(), get()) }

    viewModel { (artistName: String, albumName: String) -> AlbumDetailViewModel(artistName, albumName, get(), get()) }

}