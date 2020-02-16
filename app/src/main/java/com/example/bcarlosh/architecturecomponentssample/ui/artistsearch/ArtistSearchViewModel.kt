package com.example.bcarlosh.architecturecomponentssample.ui.artistsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallStatus
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import com.example.bcarlosh.architecturecomponentssample.ui.error.ErrorMessageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ArtistSearchViewModel(
    private val lastFmRepository: LastFmRepository,
    private val errorMessageProvider: ErrorMessageProvider
) : ViewModel() {

    var currentQueryValue: String = ""

    private val _artistSearchResponse = MutableLiveData<CallStatus<ArtistSearchResponse>>()

    val artistSearchResponse: LiveData<CallStatus<ArtistSearchResponse>> get() = _artistSearchResponse


    fun searchArtist(artist: String) = viewModelScope.launch(Dispatchers.IO) {
        _artistSearchResponse.postValue(CallStatus.Loading)

        runCatching { lastFmRepository.getArtistSearchByName(artist) }
            .onSuccess { _artistSearchResponse.postValue(CallStatus.Success(it)) }
            .onFailure { _artistSearchResponse.postValue(CallStatus.Error(errorMessageProvider.proceed(it), it)) }
    }

}
