package org.bz.autoorganizer.ui.autostate

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class AutoStateViewModel : ViewModel() {

    val progress: Flow<Int>
        get() = _progress
            .asStateFlow()
    val errors: Flow<Exception>
        get() = _errors
            .asSharedFlow()

    private val _progress = MutableStateFlow(0)
    private val _errors = MutableSharedFlow<Exception>()
    private var counter = 0
}