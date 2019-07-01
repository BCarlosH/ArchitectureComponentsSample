package com.example.bcarlosh.architecturecomponentssample.ui.albumdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallResult
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import com.example.bcarlosh.architecturecomponentssample.ui.base.BaseScopedViewModel
import kotlinx.coroutines.launch


class AlbumDetailViewModel(
    private val artistName: String,
    private val albumName: String,
    private val lastFmRepository: LastFmRepository
) : BaseScopedViewModel() {

    private val _albumInfoResponse = MutableLiveData<AlbumInfoResponse>()
    private val _isStored = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<String>()

    val albumInfoResponse: LiveData<AlbumInfoResponse> get() = _albumInfoResponse
    val isStored: LiveData<Boolean> get() = _isStored
    val error: LiveData<String> get() = _error

    val currentAlbumName = albumName
    lateinit var currentAlbum: AlbumInfo


    init {
        initAlbumInfoCall()
        updateIsStored()
    }

    private fun initAlbumInfoCall() {
        scope.launch {
            val value = lastFmRepository.getAlbumInfo(artistName, albumName)

            when (value) {
                is CallResult.Success -> {
                    _albumInfoResponse.postValue(value.data)
                    _error.postValue(null)
                }

                is CallResult.Error -> {
                    _albumInfoResponse.postValue(null)
                    _error.postValue(value.exception.message)
                }
            }
        }
    }

    private fun updateIsStored() {
        scope.launch {
            val value = lastFmRepository.isAlbumStored(albumName)

            _isStored.postValue(value)
        }
    }

    fun storeAlbum() {
        scope.launch {
            lastFmRepository.storeAlbum(currentAlbum)
            updateIsStored()
        }
    }

    fun deleteAlbum() {
        scope.launch {
            lastFmRepository.deleteStoredAlbum(currentAlbumName)
            updateIsStored()
        }
    }

}
