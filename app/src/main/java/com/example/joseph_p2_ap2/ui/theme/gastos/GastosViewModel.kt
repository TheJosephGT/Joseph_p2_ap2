package com.example.joseph_p2_ap2.ui.theme.gastos

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.joseph_p2_ap2.data.remote.dto.GastoDTO
import com.example.joseph_p2_ap2.data.repository.GastosRepository
import com.example.joseph_p2_ap2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class GastoListState(
    val isLoading: Boolean = false,
    val gastos: List<GastoDTO> = emptyList(),
    val error: String = "",
)

data class GastoState(
    val isLoading: Boolean = false,
    val gasto: GastoDTO? = null,
    val error: String = "",
)

@HiltViewModel
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class GastosViewModel @Inject constructor(
    private val gastosRepository: GastosRepository,
) : ViewModel() {
    var idGasto by mutableStateOf(0)
    var fecha by mutableStateOf("")
    var suplidor by mutableStateOf("")
    var ncf by mutableStateOf("")
    var concepto by mutableStateOf("")
    var itbis by mutableStateOf(0)
    var monto by mutableStateOf(0)
    var idSuplidor by mutableStateOf(1)
    var descuento by mutableStateOf(50)
    val suplidorList = listOf("CLARO", "ALTICE", "CLARO DOMINICANA", "ALTICE DOMINICANA", "TELEOPERADORA DEL NORDESTE SRL", "VIEW COMUNICACIONES SRL")

    var fechaError by mutableStateOf(true)
    var suplidorError by mutableStateOf(true)
    var ncfError by mutableStateOf(true)
    var conceptoError by mutableStateOf(true)
    var itbisError by mutableStateOf(true)
    var montoError by mutableStateOf(true)
    var idSuplidorError by mutableStateOf(true)
    var descuentoError by mutableStateOf(true)


    fun validar(): Boolean {
        fechaError = fecha.isNotEmpty()
        suplidorError = suplidor.isNotEmpty()
        ncfError = ncf.isNotEmpty()
        conceptoError = concepto.isNotEmpty()
        itbisError = itbis > 0
        montoError = monto > 0
        //idSuplidorError = idSuplidor > 0
        //descuentoError = descuento > 0

//|| idSuplidor == 0 || descuento == 0
        return !(fecha == "" || suplidor == "" || ncf == "" || concepto == "" || itbis <= 0 || monto <= 0)

    }

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }

    val gastos: StateFlow<Resource<List<GastoDTO>>> = gastosRepository.getGastos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Resource.Loading()
    )

    private val _uiState = MutableStateFlow(GastoListState())
    val uiState: StateFlow<GastoListState> = _uiState.asStateFlow()

    private val uiStateGasto = MutableStateFlow(GastoState())

    init {
        loadScreen()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun loadScreen() {
        gastosRepository.getGastos().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(gastos = result.data ?: emptyList()) }
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveGasto() {
        viewModelScope.launch {
            if (validar()) {
                val gasto = GastoDTO(
                    fecha = fecha,
                    suplidor = suplidor,
                    ncf = ncf,
                    concepto = concepto,
                    itbis = itbis,
                    monto = monto,
                    idSuplidor = idSuplidor,
                    descuento = descuento
                )
                gastosRepository.postGasto(gasto)
                limpiar()
                loadScreen()
            }
        }
    }

    fun updateGasto() {
        viewModelScope.launch {
            if (idGasto != 0) {
                val gasto = GastoDTO(
                    idGasto = idGasto,
                    fecha = fecha,
                    suplidor = suplidor,
                    ncf = ncf,
                    concepto = concepto,
                    itbis = itbis,
                    monto = monto,
                    idSuplidor = idSuplidor,
                    descuento = descuento
                )
                gastosRepository.putGasto(idGasto, gasto)
                limpiar()
            }
        }
    }

    fun deleteGasto(id: Int) {
        viewModelScope.launch {
            gastosRepository.deleteGasto(id)
            loadScreen()
        }
    }

    fun getGastoId(id: Int) {
        idGasto = id
        limpiar()
        gastosRepository.getGastoId(idGasto).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiStateGasto.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    uiStateGasto.update {
                        it.copy(gasto = result.data)
                    }
                    fecha = uiStateGasto.value.gasto!!.fecha.toString()
                    suplidor = uiStateGasto.value.gasto!!.suplidor.toString()
                    ncf = uiStateGasto.value.gasto!!.ncf!!
                    concepto = uiStateGasto.value.gasto!!.concepto.toString()
                    itbis = uiStateGasto.value.gasto!!.itbis!!
                    monto = uiStateGasto.value.gasto!!.monto!!
                }

                is Resource.Error -> {
                    uiStateGasto.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun limpiar() {
        fecha = ""
        suplidor = ""
        ncf = ""
        concepto = ""
        itbis = 0
        monto = 0
    }

}