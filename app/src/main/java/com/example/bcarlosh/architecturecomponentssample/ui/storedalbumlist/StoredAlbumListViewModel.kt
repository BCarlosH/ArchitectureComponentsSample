package com.example.bcarlosh.architecturecomponentssample.ui.storedalbumlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StoredAlbumListViewModel(
    private val lastFmRepository: LastFmRepository
) : ViewModel() {

    private val _storedAlbumsList =
        MutableLiveData<List<AlbumInfo>>()

    val storedAlbumsList: LiveData<List<AlbumInfo>> get() = _storedAlbumsList


    fun getStoredAlbumsList() {
        viewModelScope.launch(Dispatchers.IO) {
            val value = lastFmRepository.getStoredAlbums()

            _storedAlbumsList.postValue(value)
        }
    }

}
