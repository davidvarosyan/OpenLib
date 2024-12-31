package com.test.openlib

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.test.openlib.di.commonModule
import com.test.openlib.di.platformModule
import com.test.openlib.ui.view.BookSearchScreen
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin


class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}


@Composable
fun App() {
    KoinApplication(
        application = { modules(platformModule + commonModule).apply {  } },
        content = { NavHost() })

}

@Composable
fun NavHost() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Main) }

    when (val screen = currentScreen) {
        is Screen.Main -> BookSearchScreen() { selectedItem ->
            currentScreen = Screen.Details(selectedItem)
        }

        is Screen.Details -> {

        }
    }
}

sealed class Screen {
    object Main : Screen()
    data class Details(val itemId: String) : Screen()
}

