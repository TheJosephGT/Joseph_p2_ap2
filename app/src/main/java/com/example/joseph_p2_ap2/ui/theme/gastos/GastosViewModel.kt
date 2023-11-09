package com.example.joseph_p2_ap2.ui.theme.gastos

import android.os.Build
import androidx.annotation.RequiresExtension
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
    val gastos: GastoDTO? = null,
    val error: String = "",
)

@HiltViewModel
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class GastosViewModel @Inject constructor(private val gastosRepository: GastosRepository
) : ViewModel(){
    var idGasto by mutableIntStateOf(0)
    var fecha by mutableStateOf("")
    var suplidor by mutableStateOf("")
    var ncf by mutableStateOf("")
    var concepto by mutableStateOf("")
    var itbis by mutableIntStateOf(0)
    var monto by mutableIntStateOf(0)

    var fechaError by mutableStateOf(true)
    var suplidorError by mutableStateOf(true)
    var ncfError by mutableStateOf(true)
    var conceptoError by mutableStateOf(true)
    var itbisError by mutableStateOf(true)
    var montoError by mutableStateOf(true)


    fun validar() : Boolean{
        fechaError = fecha.isNotEmpty()
        suplidorError = suplidor.isNotEmpty()
        ncfError = ncf.isNotEmpty()
        conceptoError = concepto.isNotEmpty()
        itbisError = itbis <= 0
        montoError = monto <= 0

        return !(fecha == "" || suplidor == "" || ncf == "" || concepto == "" || itbis == 0 || monto == 0)

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
            val gasto = GastoDTO(
                fecha = fecha,
                suplidor = suplidor,
                ncf = ncf,
                concepto = concepto,
                itbis = itbis,
                monto = monto
            )
            gastosRepository.postGasto(gasto)
            limpiar()
        }
    }

    fun updateGasto() {
        viewModelScope.launch {
            val gasto = GastoDTO(
                idGasto = idGasto,
                fecha = fecha,
                suplidor = suplidor,
                ncf = ncf,
                concepto = concepto,
                itbis = itbis,
                monto = monto
            )
            gastosRepository.putGasto(idGasto,gasto)
            limpiar()
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
                        it.copy(gastos = result.data)
                    }
                    fecha = uiStateGasto.value.gastos!!.fecha
                    suplidor = uiStateGasto.value.gastos!!.suplidor
                    ncf = uiStateGasto.value.gastos!!.ncf
                    concepto = uiStateGasto.value.gastos!!.concepto
                    itbis = uiStateGasto.value.gastos!!.itbis!!
                    monto = uiStateGasto.value.gastos!!.monto!!
                }
                is Resource.Error -> {
                    uiStateGasto.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun limpiar(){}

}