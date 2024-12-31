package com.test.openlib.data.db

import android.content.Context

actual class DataStorePathProvider(private val context: Context) {
    actual fun providePath(): String {
        return context.filesDir.absolutePath
    }
}