package com.example.bcarlosh.architecturecomponentssample.data.network

import com.example.bcarlosh.architecturecomponentssample.data.network.interceptor.ConnectivityInterceptor
import com.example.bcarlosh.architecturecomponentssample.data.network.response.AlbumInfoResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.ArtistSearchResponse
import com.example.bcarlosh.architecturecomponentssample.data.network.response.TopAlbumsResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "http://ws.audioscrobbler.com/2.0/"
const val JSON_FORMAT_QUERY_PARAMETER = "json"
const val API_KEY = "292909677dcdb20e1a3f9c0a81882636"


interface LastFmApiService {

    @GET("?method=artist.search")
    fun searchArtistByNameAsync(
        @Query("artist") artist: String
    ): Deferred<Response<ArtistSearchResponse>>

    @GET("?method=artist.getTopAlbums")
    fun getArtistTopAlbumsAsync(
        @Query("artist") artist: String
    ): Deferred<Response<TopAlbumsResponse>>

    @GET("?method=album.getinfo")
    fun getAlbumInfoAsync(
        @Query("artist") artist: String,
        @Query("album") album: String
    ): Deferred<Response<AlbumInfoResponse>>


    companion object {

        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): LastFmApiService {
            val requestInterceptor = Interceptor { chain ->

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

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(LastFmApiService::class.java)
        }

    }

}