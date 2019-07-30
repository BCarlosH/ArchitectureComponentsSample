package com.example.bcarlosh.architecturecomponentssample.data.network.response


/**
 * This class is used to wrap-up the response in Success and in the Error case.
 */
sealed class CallStatus<out T> {

    object Loading : CallStatus<Nothing>()

    data class Success<out T>(val data: T) : CallStatus<T>()

    data class Error(val throwable: Throwable) : CallStatus<Nothing>()

}