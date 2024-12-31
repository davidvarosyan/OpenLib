package com.test.openlib.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.test.openlib.data.db.DataStoreInitializer
import com.test.openlib.data.repository.BookDetailsRepositoryImpl
import com.test.openlib.data.repository.BookSearchRepositoryImpl
import com.test.openlib.data.repository.NetworkRepositoryImpl
import com.test.openlib.data.repository.SearchActionRepoImpl
import com.test.openlib.db.BookDatabase
import com.test.openlib.domain.repository.BookDetailsRepository
import com.test.openlib.domain.repository.BookSearchRepository
import com.test.openlib.domain.repository.NetworkRepository
import com.test.openlib.domain.repository.SearchActionRepo
import com.test.openlib.domain.usecase.BookDetailsUseCase
import com.test.openlib.domain.usecase.BookDetailsUseCaseImpl
import com.test.openlib.domain.usecase.BookSearchUseCase
import com.test.openlib.domain.usecase.BookSearchUseCaseImpl
import com.test.openlib.domain.usecase.SearchActionUseCase
import com.test.openlib.domain.usecase.SearchActionUseCaseImpl
import com.test.openlib.ui.viewmodel.BookDetailsViewModel
import com.test.openlib.ui.viewmodel.BookSearchViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.jvm.JvmName


@JvmName("initKoin")
fun koinInit(): KoinApplication = startKoin {
    commonModule + platformModule
}


expect val platformModule: Module

val commonModule = module {

    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }


    single<BookDatabase> { BookDatabase.invoke(driver = get()) }

    factory<BookSearchUseCase> {
        BookSearchUseCaseImpl(bookSearchRepository = get())
    }

    factory<BookDetailsUseCase> {
        BookDetailsUseCaseImpl(repo = get())
    }

    factory<BookDetailsRepository> { BookDetailsRepositoryImpl() }

    factory<BookSearchRepository> {
        BookSearchRepositoryImpl(
            apiService = get(),
            bookDatabase = get(),
            networkRepository = get(),
            dataStore = get()
        )
    }

    factory<SearchActionUseCase> { SearchActionUseCaseImpl(searchActionRepo = get()) }

    factory<SearchActionRepo> { SearchActionRepoImpl(dataStore = get()) }

    viewModel<BookSearchViewModel> {
        BookSearchViewModel(bookSearchUseCase = get(), get())
    }

    viewModel<BookDetailsViewModel> {
        BookDetailsViewModel(bookDetailUseCase = get())
    }

    single<NetworkRepository> { NetworkRepositoryImpl(networkHandler = get()) }

    single { DataStoreInitializer(pathProvider = get()) }

    single<DataStore<Preferences>> { get<DataStoreInitializer>().createDataStore() }
}