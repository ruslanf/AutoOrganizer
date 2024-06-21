package org.bz.autoorganizer.ui.profile.yandex

import android.app.Application
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class YandexOAuthViewModel(
    app: Application
) : ViewModel() {

    val sdk = YandexAuthSdk.create(YandexAuthOptions(app.applicationContext))
    val loginOptions = YandexAuthLoginOptions()

    val watchYandexAuthState: Flow<YandexAuthState>
        get() = _yandexAuthState
    val watchYandexToken: Flow<String>
        get() = _yandexToken

    private val _yandexAuthState = MutableStateFlow<YandexAuthState>(YandexAuthState.NeutralState)
    private val _yandexToken = MutableSharedFlow<String>()

    init {
        viewModelScope.launch {
            launch {
                _yandexAuthState
                    .filter { it is YandexAuthState.ProcessSuccessState }
                    .collect { state ->
                        _yandexToken.emit(
                            sdk.getJwt((state as YandexAuthState.ProcessSuccessState).token)
                        )
                    }
            }
        }
    }

    fun handleResult(result: YandexAuthResult) {
        viewModelScope.launch {
            _yandexAuthState.emit(
                when (result) {
                    YandexAuthResult.Cancelled -> YandexAuthState.CancelState
                    is YandexAuthResult.Failure -> YandexAuthState.ProcessErrorState(result.exception)
                    is YandexAuthResult.Success -> YandexAuthState.ProcessSuccessState(result.token)
                }
            )
        }
    }
}