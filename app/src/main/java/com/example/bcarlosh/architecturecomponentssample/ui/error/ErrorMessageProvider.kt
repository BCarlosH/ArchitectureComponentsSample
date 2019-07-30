package com.example.bcarlosh.architecturecomponentssample.ui.error


interface ErrorMessageProvider {

    fun proceed(error: Throwable): String

}