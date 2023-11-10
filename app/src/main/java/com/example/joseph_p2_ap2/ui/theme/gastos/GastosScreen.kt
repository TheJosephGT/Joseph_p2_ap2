package com.example.joseph_p2_ap2.ui.theme.gastos

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.example.joseph_p2_ap2.data.remote.dto.Suplidor
import com.example.joseph_p2_ap2.data.remote.dto.suplidores
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GastosScreen(viewModel: GastosViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest { showMessage ->
            if (showMessage) {
                snackbarHostState.showSnackbar(
                    message = "Guardado correctamente",
                    duration = SnackbarDuration.Long
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Registro de gastos",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomOutlinedTextField(
                    value = viewModel.concepto,
                    onValueChange = { viewModel.concepto = it },
                    label = "Concepto",
                    isError = true,
                    imeAction = ImeAction.Next
                )
                if (!viewModel.conceptoError) {
                    Text(text = "El campo concepto esta vacío", color = Color.Red)
                }
            }
        }

        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = selectedItem,
                    onValueChange = {
                        selectedItem = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFiledSize = coordinates.size.toSize()
                        },
                    label = { Text(text = "Suplidor") },
                    trailingIcon = {
                        Icon(icon, "", Modifier.clickable { expanded = !expanded })
                    },
                    readOnly = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )
                DropdownMenu(expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(
                        with(LocalDensity.current) { textFiledSize.width.toDp() }
                    )
                ) {
                    viewModel.suplidorList.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = {
                            selectedItem = label
                            expanded = false
                            viewModel.suplidor = selectedItem
                        })
                    }
                }
                if (!viewModel.suplidorError) {
                    Text(text = "El campo Suplidor esta vacío", color = Color.Red)
                }
            }
        }

        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomOutlinedTextField(
                    value = viewModel.ncf,
                    onValueChange = { viewModel.ncf = it },
                    label = "Ncf",
                    isError = true,
                    imeAction = ImeAction.Next
                )
                if (!viewModel.ncfError) {
                    Text(text = "El campo ncf esta vacío", color = Color.Red)
                }
            }
        }

        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.itbis.toString(),
                    label = { Text(text = "Itbis") },
                    singleLine = true,
                    onValueChange = {
                        val newValue = it.toIntOrNull()
                        if (newValue != null) {
                            viewModel.itbis = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                if (!viewModel.itbisError) {
                    Text(
                        text = "El campo itbis esta vacío o tiene un valor invalido",
                        color = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.width(30.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.monto.toString(),
                    label = { Text(text = "Monto") },
                    singleLine = true,
                    onValueChange = {
                        val newValue = it.toIntOrNull()
                        if (newValue != null) {
                            viewModel.monto = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                if (!viewModel.montoError) {
                    Text(
                        text = "El campo monto esta vacío o tiene un valor invalido",
                        color = Color.Red
                    )
                }
            }
        }

        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomOutlinedTextField(
                    value = viewModel.fecha,
                    onValueChange = { viewModel.fecha = it },
                    label = "Fecha",
                    isError = true,
                    imeAction = ImeAction.Next
                )
                if (!viewModel.fechaError) {
                    Text(text = "El campo fecha esta vacío", color = Color.Red)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Button(onClick = {
                    keyboardController?.hide()
                    if (viewModel.uiStateGasto.value.gasto != null)
                        viewModel.updateGasto()
                    else
                        viewModel.saveGasto()
                    viewModel.setMessageShown()
                    if (viewModel.validar())
                        selectedItem = ""
                }, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium)
                {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Guardar")
                    Text(text = "Guardar")
                }

                uiState.gastos?.let { gastos ->
                    Consult(
                        gastos,
                        onUpdate = { gastos -> gastos.idGasto?.let { viewModel.getGastoId(it) } })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    imeAction: ImeAction,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label) },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isError) Color.Gray else Color.Red,
            unfocusedBorderColor = if (isError) Color.Gray else Color.Red
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction)
    )
}

