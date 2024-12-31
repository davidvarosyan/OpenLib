package com.test.openlib.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.test.openlib.db.BookDatabase

actual class SqlDriverFactory {
    actual fun createDriver(): SqlDriver {
      return  NativeSqliteDriver(BookDatabase.Schema, "books.db")
    }
}