package org.bz.autoorganizer.ui.profile.yandex

import com.yandex.authsdk.YandexAuthToken

sealed class YandexAuthState {
    data object NeutralState : YandexAuthState()
    data object CancelState : YandexAuthState()
    data class ProcessErrorState(val exception: Exception) : YandexAuthState()
    data class ProcessSuccessState(val token: YandexAuthToken) : YandexAuthState()
}