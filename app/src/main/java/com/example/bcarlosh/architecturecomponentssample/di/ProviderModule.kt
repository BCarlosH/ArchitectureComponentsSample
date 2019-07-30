package com.example.bcarlosh.architecturecomponentssample.di

import com.example.bcarlosh.architecturecomponentssample.helpers.ResourceProvider
import com.example.bcarlosh.architecturecomponentssample.ui.error.ErrorMessageProvider
import com.example.bcarlosh.architecturecomponentssample.ui.error.ErrorMessageProviderImpl
import org.koin.dsl.module


val providerModule = module {

    single<ErrorMessageProvider> { ErrorMessageProviderImpl(get()) }

    single { ResourceProvider(get()) }

}