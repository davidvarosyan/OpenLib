package com.test.openlib.data.db

import app.cash.sqldelight.db.SqlDriver

expect class SqlDriverFactory {
    fun createDriver(): SqlDriver

}