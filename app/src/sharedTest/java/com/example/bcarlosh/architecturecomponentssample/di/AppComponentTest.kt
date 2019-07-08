package com.example.bcarlosh.architecturecomponentssample.di


fun configureAppComponent(baseApi: String) = listOf(
    configureNetworkModuleForTest(baseApi),
    dbModule,
    viewModelFactoryModule,
    repositoryModule
)