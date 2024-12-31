package com.test.openlib.data.db

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

class DataStoreInitializer(private val pathProvider: DataStorePathProvider) {
    fun createDataStore(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(produceFile = { (pathProvider.providePath() + "/${PREFS_FILE_NAME}").toPath() })
    }

    companion object {
        internal const val PREFS_FILE_NAME = "prefs.preferences_pb"

    }

}


