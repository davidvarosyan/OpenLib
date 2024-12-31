package com.test.openlib.ui.common

sealed class LoadingState {
    data object EmptyState : LoadingState()
    data object NoLoading : LoadingState()
    data object Refreshing : LoadingState()
    data object Loading : LoadingState()

}