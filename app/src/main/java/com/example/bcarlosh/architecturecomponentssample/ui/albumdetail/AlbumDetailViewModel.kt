package com.example.bcarlosh.architecturecomponentssample.ui.albumdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bcarlosh.architecturecomponentssample.data.entity.album.AlbumInfo
import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallStatus
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import com.example.bcarlosh.architecturecomponentssample.ui.error.ErrorMessageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlbumDetailViewModel(
    private val artistName: String,
    private val albumName: String,
    private val lastFmRepository: LastFmRepository,
    private val errorMessageProvider: ErrorMessageProvider
) : ViewModel() {

    private val _albumInfoResponse = MutableLiveData<CallStatus<AlbumInfoResponse>>()
    private val _isStored = MutableLiveData<Boolean>()

    val albumInfoResponse: LiveData<CallStatus<AlbumInfoResponse>> get() = _albumInfoResponse
    val isStored: LiveData<Boolean> get() = _isStored

    val currentAlbumName = albumName
    lateinit var currentAlbum: AlbumInfo


    /**
     * The LiveData's initialization remains inside the "init block" until Koin officially supports
     * the ViewModel Saved State module.
     */
    init {
        initAlbumInfoCall()
        updateIsStored()
    }

    private fun initAlbumInfoCall() = viewModelScope.launch(Dispatchers.IO) {
        _albumInfoResponse.postValue(CallStatus.Loading)

        runCatching { lastFmRepository.getAlbumInfo(artistName, albumName) }
            .onSuccess { _albumInfoResponse.postValue(CallStatus.Success(it)) }
            .onFailure { _albumInfoResponse.postValue(CallStatus.Error(errorMessageProvider.proceed(it), it)) }
    }

    private fun updateIsStored() {
        viewModelScope.launch(Dispatchers.IO) {
            val value = lastFmRepository.isAlbumStored(albumName)

            _isStored.postValue(value)
        }
    }

    fun storeAlbum() {
        viewModelScope.launch(Dispatchers.IO) {
            lastFmRepository.storeAlbum(currentAlbum)
            updateIsStored()
        }
    }

    fun deleteAlbum() {
        viewModelScope.launch(Dispatchers.IO) {
            lastFmRepository.deleteStoredAlbum(currentAlbumName)
            updateIsStored()
        }
    }

}
