package com.example.bcarlosh.architecturecomponentssample.helpers

import com.example.bcarlosh.architecturecomponentssample.data.network.response.CallResult
import java.io.IOException


/**
 * For the sake of adding the try/catch to every Network request,
 * we create a safeApiCall top-level function to just trigger the request.
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> CallResult<T>, errorMessage: String): CallResult<T> = try {
    call.invoke()
} catch (e: Exception) {
    CallResult.Error(IOException(errorMessage, e))
}