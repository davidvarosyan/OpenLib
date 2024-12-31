package com.test.openlib.di

import app.cash.sqldelight.db.SqlDriver
import com.test.openlib.data.db.DataStorePathProvider
import com.test.openlib.data.db.SqlDriverFactory
import com.test.openlib.data.repository.NetworkHandler
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SqlDriver> {
        SqlDriverFactory(context = androidContext()).createDriver()
    }

    single<NetworkHandler> { NetworkHandler(context = androidContext()) }

    single { DataStorePathProvider(androidContext()) }
}


