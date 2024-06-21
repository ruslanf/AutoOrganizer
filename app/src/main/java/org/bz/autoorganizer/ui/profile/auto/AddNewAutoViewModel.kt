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
import org.bz.autoorganizer.data.models.Auto
import org.bz.autoorganizer.data.models.AutoModel
import org.bz.autoorganizer.data.repository.DataBase
import org.bz.autoorganizer.data.repository.NetworkBase
import timber.log.Timber

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

    val watchAutoManufacturers: Flow<List<String>>
        get() = _autoManufacturers
            .asSharedFlow()
    val watchAutoModels: Flow<List<AutoModel>>
        get() = _autoModels
            .asSharedFlow()

    private val _autoManufacturers = MutableSharedFlow<List<String>>()
    private val _autos = MutableSharedFlow<List<Auto>>()
    private val _autoModels = MutableSharedFlow<List<AutoModel>>()

    private val _progress = MutableStateFlow(0)
    private val _errors = MutableSharedFlow<Exception>()
    private var counter = 0

    private var manufacturers = mutableListOf<String>()
    private var autos = mutableListOf<Auto>()

    init {
        viewModelScope.launch {
            launch(Dispatchers.IO) { fetchAutoModels() }
        }
    }

    suspend fun doClearModels() {
        Timber.i("doClearModels()...")
        _autoModels.emit(emptyList())
    }

    suspend fun getModelByManufacturer(manufacturer: String) {
        Timber.i("getModelByManufacturer()...")
        autos
            .filter { it.name == manufacturer }
            .map { models ->
                models.models?.also { _autoModels.emit(it) }
            }
    }

    private suspend fun fetchAutoModels() {
        counter += 1
        _progress.emit(counter)

        when (val r = networkBase.getAutoModels()) {
            is Left -> _errors.emit(r.value)
            is Right -> {
                r.value.map { auto ->
                    this.autos.add(auto)
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