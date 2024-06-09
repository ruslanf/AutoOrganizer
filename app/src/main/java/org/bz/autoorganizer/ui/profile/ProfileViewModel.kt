package org.bz.autoorganizer.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import org.bz.autoorganizer.data.http.Left
import org.bz.autoorganizer.data.http.Right
import org.bz.autoorganizer.data.repository.DataBase
import org.bz.autoorganizer.data.repository.NetworkBase
import timber.log.Timber

class ProfileViewModel(
    private val dataBase: DataBase,
    private val networkBase: NetworkBase
) : ViewModel() {

    val progress: Flow<Int>
        get() = _progress
            .asStateFlow()
    val errors: Flow<Exception>
        get() = _errors
            .asSharedFlow()

    private val _progress = MutableStateFlow(0)
    private val _errors = MutableSharedFlow<Exception>()
    private var counter = 0

    init {
        viewModelScope.launch {
            launch { fetchAutoModels() }
        }
    }

    private suspend fun fetchAutoModels() {
        counter += 1
        _progress.emit(counter)

        when (val r = networkBase.getAutoModels()) {
            is Left -> _errors.emit(r.value)
            is Right -> {
                r.value.forEach { println(it) }
            }
        }

        counter -= 1
        _progress.emit(counter)
    }
}