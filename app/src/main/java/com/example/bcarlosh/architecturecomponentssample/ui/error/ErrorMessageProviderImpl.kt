package com.example.bcarlosh.architecturecomponentssample.ui.error

import com.example.bcarlosh.architecturecomponentssample.R
import com.example.bcarlosh.architecturecomponentssample.helpers.ResourceProvider


class ErrorMessageProviderImpl(private val resourceProvider: ResourceProvider) : ErrorMessageProvider {

    override fun proceed(error: Throwable): String {

        return when (error.message) {
            ErrorType.CONNECTIVITY.name -> resourceProvider.getString(R.string.connectivity_error)
            ErrorType.FETCHING_ARTIST_SEARCH.name -> resourceProvider.getString(R.string.network_error_on_artist_search)
            ErrorType.FETCHING_TOP_ALBUMS.name -> resourceProvider.getString(R.string.network_error_on_top_albums)
            ErrorType.FETCHING_ALBUM_INFO.name -> resourceProvider.getString(R.string.network_error_on_album_info)
            else -> resourceProvider.getString(R.string.generic_error)
        }

    }

}