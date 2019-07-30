package com.example.bcarlosh.architecturecomponentssample.ui.artisttopalbum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallStatus
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import com.example.bcarlosh.architecturecomponentssample.ui.base.BaseScopedViewModel
import kotlinx.coroutines.launch


class ArtistTopAlbumsViewModel(
    private val artistName: String,
    private val lastFmRepository: LastFmRepository
) : BaseScopedViewModel() {

    private val _topAlbumsListResponse = MutableLiveData<CallStatus<TopAlbumsResponse>>()

    val topAlbumsListResponse: LiveData<CallStatus<TopAlbumsResponse>> get() = _topAlbumsListResponse


    /**
     * The LiveData's initialization remains inside the "init block" until Koin officially supports
     * the ViewModel Saved State module.
     */
    init {
        getTopAlbums()
    }

    fun getTopAlbums() = scope.launch {
        _topAlbumsListResponse.postValue(CallStatus.Loading)

        runCatching { lastFmRepository.getArtistTopAlbums(artistName) }
            .onSuccess { _topAlbumsListResponse.postValue(CallStatus.Success(it)) }
            .onFailure { _topAlbumsListResponse.postValue(CallStatus.Error(it)) }
    }

}
