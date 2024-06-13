package org.bz.autoorganizer.ui.profile.auto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.bz.autoorganizer.data.http.Left
import org.bz.autoorganizer.data.http.Right
import org.bz.autoorganizer.data.repository.DataBase
import org.bz.autoorganizer.data.repository.NetworkBase

class AddNewAutoViewModel(
    private val dataBase: DataBase,
    private val networkBase: NetworkBase
) : ViewModel() {

    val progress: Flow<Int>
        get() = _progress
            .asStateFlow()
    val errors: Flow<Exception>
        get() = _errors
            .asSharedFlow()

    val autoManufacturers: Flow<List<String>>
        get() = _autoManufacturers
            .asSharedFlow()

    private val _autoManufacturers = MutableSharedFlow<List<String>>()

    private val _progress = MutableStateFlow(0)
    private val _errors = MutableSharedFlow<Exception>()
    private var counter = 0

    private var manufacturers = mutableListOf<String>()

    init {
        viewModelScope.launch {
            launch(Dispatchers.IO) { fetchAutoModels() }
        }
    }

    private suspend fun fetchAutoModels() {
        counter += 1
        _progress.emit(counter)

        when (val r = networkBase.getAutoModels()) {
            is Left -> _errors.emit(r.value)
            is Right -> {
                val autos = r.value
                autos.map { auto ->
                    auto.name?.also { name ->
                        manufacturers.add(name)
                    }
                }
                _autoManufacturers.emit(manufacturers)
            }
        }

        counter -= 1
        _progress.emit(counter)
    }
}