package com.example.bcarlosh.architecturecomponentssample.di

import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmApiService
import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmNetworkDataSource
import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmNetworkDataSourceImpl
import com.example.bcarlosh.architecturecomponentssample.data.network.interceptor.ConnectivityInterceptor
import com.example.bcarlosh.architecturecomponentssample.data.network.interceptor.ConnectivityInterceptorImpl
import org.koin.dsl.module


val networkModule = module {

    single { LastFmApiService(get()) }

    single<LastFmNetworkDataSource> { LastFmNetworkDataSourceImpl(get()) }

    single<ConnectivityInterceptor> { ConnectivityInterceptorImpl(get()) }

}