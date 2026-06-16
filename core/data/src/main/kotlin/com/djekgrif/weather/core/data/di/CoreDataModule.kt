package com.djekgrif.weather.core.data.di

import com.djekgrif.weather.core.data.connectivity.AndroidConnectivityObserver
import com.djekgrif.weather.core.data.connectivity.ConnectivityObserver
import com.djekgrif.weather.core.data.network.HttpClientFactory
import com.djekgrif.weather.core.domain.dispatcher.DispatcherProvider
import com.djekgrif.weather.core.domain.dispatcher.StandardDispatcherProvider
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    singleOf(::StandardDispatcherProvider) { bind<DispatcherProvider>() }
    single<ConnectivityObserver> { AndroidConnectivityObserver(androidContext()) }
}