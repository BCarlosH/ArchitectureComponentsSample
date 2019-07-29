package com.example.bcarlosh.architecturecomponentssample.di

import android.util.Log
import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmApiService
import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmNetworkDataSource
import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmNetworkDataSourceImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun configureNetworkModuleForTest(baseApi: String) = module {

    //region Retrofit
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory { get<Retrofit>().create(LastFmApiService::class.java) }
    //endregion

    //region Interceptors
    factory<Interceptor> {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API-TESTING: ", it) })
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory { OkHttpClient.Builder().addInterceptor(get()).build() }
    //endregion

    //region DataSource
    single<LastFmNetworkDataSource> { LastFmNetworkDataSourceImpl(get()) }
    //endregion

}