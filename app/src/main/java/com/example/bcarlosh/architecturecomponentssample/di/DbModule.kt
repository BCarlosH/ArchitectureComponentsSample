package com.example.bcarlosh.architecturecomponentssample.di

import androidx.room.Room
import com.example.bcarlosh.architecturecomponentssample.data.db.LastFmDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dbModule = module {

    single {
        Room.databaseBuilder(androidContext(), LastFmDataBase::class.java, "lastFm-db").build()
    }

    single { get<LastFmDataBase>().albumDao() }

}