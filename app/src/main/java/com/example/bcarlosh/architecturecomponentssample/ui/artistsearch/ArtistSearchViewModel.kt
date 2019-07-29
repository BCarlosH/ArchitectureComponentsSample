package com.example.bcarlosh.architecturecomponentssample.ui.artistsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallResult
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import com.example.bcarlosh.architecturecomponentssample.ui.base.BaseScopedViewModel
import kotlinx.coroutines.launch


class ArtistSearchViewModel(
    private val lastFmRepository: LastFmRepository
) : BaseScopedViewModel() {

    var currentQueryValue: String = ""

    private val _artistSearchResponse = MutableLiveData<ArtistSearchResponse>()
    private val _error = MutableLiveData<String>()

    val artistSearchResponse: LiveData<ArtistSearchResponse> get() = _artistSearchResponse
    val error: LiveData<String> get() = _error


    fun searchArtist(artist: String) = scope.launch {
        val value = lastFmRepository.getArtistSearchByName(artist)

        when (value) {
            is CallResult.Success -> {
                _artistSearchResponse.postValue(value.data)
                _error.postValue(null)
            }

            is CallResult.Error -> {
                _artistSearchResponse.postValue(null)
                _error.postValue(value.exception.message)
            }
        }
    }

}
