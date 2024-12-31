package com.test.openlib.data.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.test.openlib.db.BookDatabase

actual class SqlDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(BookDatabase.Schema, context, "BookDatabase.db")
    }
}