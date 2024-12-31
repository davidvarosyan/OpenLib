package com.test.openlib.di

import app.cash.sqldelight.db.SqlDriver
import com.test.openlib.data.db.DataStorePathProvider
import com.test.openlib.data.db.SqlDriverFactory
import com.test.openlib.data.repository.NetworkHandler
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<SqlDriver> {
            SqlDriverFactory().createDriver()
        }


        single<NetworkHandler> {
            NetworkHandler()
        }

        single { DataStorePathProvider() }
    }