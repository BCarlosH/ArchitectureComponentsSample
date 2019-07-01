package com.example.bcarlosh.architecturecomponentssample.ui.artisttopalbum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallResult
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import com.example.bcarlosh.architecturecomponentssample.ui.base.BaseScopedViewModel
import kotlinx.coroutines.launch


class ArtistTopAlbumsViewModel(
    private val artistName: String,
    private val lastFmRepository: LastFmRepository
) : BaseScopedViewModel() {

    private val _topAlbumsListResponse = MutableLiveData<TopAlbumsResponse>()
    private val _error = MutableLiveData<String>()

    val topAlbumsListResponse: LiveData<TopAlbumsResponse> get() = _topAlbumsListResponse
    val error: LiveData<String> get() = _error


    init {
        initTopAlbumsCall()
    }

    private fun initTopAlbumsCall() {
        scope.launch {
            val value = lastFmRepository.getArtistTopAlbums(artistName)

            when (value) {
                is CallResult.Success -> {
                    _topAlbumsListResponse.postValue(value.data)
                    _error.postValue(null)
                }

                is CallResult.Error -> {
                    _topAlbumsListResponse.postValue(null)
                    _error.postValue(value.exception.message)
                }
            }
        }
    }

}
