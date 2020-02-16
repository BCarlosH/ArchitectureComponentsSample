package com.example.bcarlosh.architecturecomponentssample.di

import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmApiService
import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmNetworkDataSource
import com.example.bcarlosh.architecturecomponentssample.data.network.LastFmNetworkDataSourceImpl
import com.example.bcarlosh.architecturecomponentssample.data.network.interceptor.ConnectivityInterceptor
import com.example.bcarlosh.architecturecomponentssample.data.network.interceptor.ConnectivityInterceptorImpl
import com.example.bcarlosh.architecturecomponentssample.di.NetworkDataSourceProperties.API_KEY
import com.example.bcarlosh.architecturecomponentssample.di.NetworkDataSourceProperties.BASE_URL
import com.example.bcarlosh.architecturecomponentssample.di.NetworkDataSourceProperties.JSON_FORMAT_QUERY_PARAMETER
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    //region Retrofit
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(LastFmApiService::class.java) }
    //endregion

    //region DataSource
    single<LastFmNetworkDataSource> { LastFmNetworkDataSourceImpl(get()) }
    //endregion

    //region OkHttpClient
    factory<ConnectivityInterceptor> { ConnectivityInterceptorImpl(get()) }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<ConnectivityInterceptor>())
            .addInterceptor(provideHttpLoggingInterceptor())
            .addInterceptor(provideQueryParametersInterceptor())
            .build()
    }
    //endregion

}

//region Interceptor providers
private fun provideHttpLoggingInterceptor(): Interceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
}

private fun provideQueryParametersInterceptor(): Interceptor {
    return Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("format", JSON_FORMAT_QUERY_PARAMETER)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }
}
//endregion


object NetworkDataSourceProperties {

    const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
    const val JSON_FORMAT_QUERY_PARAMETER = "json"
    const val API_KEY = "292909677dcdb20e1a3f9c0a81882636"

}