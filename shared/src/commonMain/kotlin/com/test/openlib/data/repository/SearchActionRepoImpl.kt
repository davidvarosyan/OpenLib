package com.test.openlib.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.test.openlib.domain.model.SearchAction
import com.test.openlib.domain.repository.SearchActionRepo
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map

class SearchActionRepoImpl(private val dataStore: DataStore<Preferences>) : SearchActionRepo {
    override suspend fun getLastAction(): SearchAction? {
        return dataStore.data.map {
            val timeKey = longPreferencesKey("edit_date")
            val subjectKey = stringPreferencesKey("subject")
            val subject = it[subjectKey]
            val editDate = it[timeKey]
            if (subject == null || editDate == null) {
                null
            } else {
                SearchAction(subject, editDate)
            }
        }.firstOrNull()
    }

    override suspend fun saveLastAction(searchAction: SearchAction) {
        dataStore.edit {
            val timeKey = longPreferencesKey("edit_date")
            val subjectKey = stringPreferencesKey("subject")
            it[timeKey] = searchAction.date
            it[subjectKey] = searchAction.subject
        }
    }
}