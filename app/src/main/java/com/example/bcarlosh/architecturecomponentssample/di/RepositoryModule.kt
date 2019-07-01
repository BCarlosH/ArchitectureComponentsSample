package com.example.bcarlosh.architecturecomponentssample.di

import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepository
import com.example.bcarlosh.architecturecomponentssample.data.repository.LastFmRepositoryImpl
import org.koin.dsl.module


val repositoryModule = module {

    single<LastFmRepository> { LastFmRepositoryImpl(get(), get()) }

}