package com.example.bcarlosh.architecturecomponentssample

import android.content.Context
import com.example.bcarlosh.architecturecomponentssample.di.*
import com.example.bcarlosh.architecturecomponentssample.ui.albumdetail.AlbumDetailViewModel
import com.example.bcarlosh.architecturecomponentssample.ui.artisttopalbum.ArtistTopAlbumsViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules


class ModuleTest {

    private lateinit var context: Context


    @Before
    fun setUp() {
        context = mock()
    }

    @Test
    fun `check all definitions from modules`() {

        // Use koinApplication instead of startKoin, to avoid having to close Koin after each test
        koinApplication {
            androidContext(context)
            modules(
                listOf(
                    dbModule,
                    networkModule,
                    repositoryModule,
                    providerModule,
                    viewModelFactoryModule
                )
            )
        }.checkModules {
            create<ArtistTopAlbumsViewModel> { parametersOf("artistName") }
            create<AlbumDetailViewModel> { parametersOf("artistName", "AlbumName") }
        }
    }

}