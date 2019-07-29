package com.example.bcarlosh.architecturecomponentssample.base

import okhttp3.mockwebserver.MockResponse
import java.io.File


abstract class BaseUT : BaseTest() {

    fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName))
    )

    private fun getJson(path: String): String {
        val uri = javaClass.classLoader?.getResource(path)
        return if (uri != null) {
            val file = File(uri.path)
            String(file.readBytes())
        } else {
            ""
        }
    }

}