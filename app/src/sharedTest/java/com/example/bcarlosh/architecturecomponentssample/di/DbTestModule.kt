package com.example.bcarlosh.architecturecomponentssample.di

import androidx.room.Room
import com.example.bcarlosh.architecturecomponentssample.data.db.LastFmDataBase
import org.koin.dsl.module


/**
 * In-memory version of the database to make the tests more hermetic.
 */
val dbTestModule = module {

    single {
        Room.inMemoryDatabaseBuilder(get(), LastFmDataBase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    single { get<LastFmDataBase>().albumDao() }

}