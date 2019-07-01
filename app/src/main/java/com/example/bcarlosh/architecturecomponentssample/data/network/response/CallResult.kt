package com.example.bcarlosh.architecturecomponentssample.data.network.response


/**
 * This class is used to wrap-up the response in Success and in the Error case.
 */
sealed class CallResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : CallResult<T>()
    data class Error(val exception: Exception) : CallResult<Nothing>()
}