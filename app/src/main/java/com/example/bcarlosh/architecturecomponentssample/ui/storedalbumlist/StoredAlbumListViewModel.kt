package com.example.bcarlosh.architecturecomponentssample.ui.storedalbumlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import com.example.bcarlosh.architecturecomponentssample.ui.base.BaseScopedViewModel
import kotlinx.coroutines.launch


class StoredAlbumListViewModel(
    private val lastFmRepository: LastFmRepository
) : BaseScopedViewModel() {

    private val _storedAlbumsList =
        MutableLiveData<List<AlbumInfo>>()

    val storedAlbumsList: LiveData<List<AlbumInfo>> get() = _storedAlbumsList


    fun getStoredAlbumsList() {
        scope.launch {
            val value = lastFmRepository.getStoredAlbums()

            _storedAlbumsList.postValue(value)
        }
    }

}
