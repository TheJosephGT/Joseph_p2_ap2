package com.example.joseph_p2_ap2.ui.theme.gastos

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.joseph_p2_ap2.util.Resource
import kotlinx.coroutines.flow.collectLatest


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GastosScreen(viewModel: GastosViewModel = hiltViewModel()) {
    val gastosResource by viewModel.gastos.collectAsState(initial = Resource.Loading())
    val gastos = gastosResource.data
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

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
        Text(text = "Registro de gastos", style = MaterialTheme.typography.titleLarge, modifier = Modifier
            .padding(8.dp)
            .align(Alignment.CenterHorizontally))
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
            }
        }

        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomOutlinedTextField(
                    value = viewModel.suplidor,
                    onValueChange = { viewModel.suplidor = it},
                    label = "Suplidor",
                    isError = true,
                    imeAction = ImeAction.Next
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.idSuplidor.toString(),
                    label = { Text(text = "idSuplidor") },
                    singleLine = true,
                    onValueChange = {
                        val newValue = it.toIntOrNull()
                        if (newValue != null) {
                            viewModel.idSuplidor = newValue
                        }
                    },
                    modifier = Modifier.fillMaxWidth().width(200.dp)
                )
            }
        }
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {

                OutlinedTextField(
                    value = viewModel.descuento.toString(),
                    label = { Text(text = "Descuento") },
                    singleLine = true,
                    onValueChange = {
                        val newValue = it.toIntOrNull()
                        if (newValue != null) {
                            viewModel.descuento = newValue
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomOutlinedTextField(
                    value = viewModel.ncf,
                    onValueChange = { viewModel.ncf = it},
                    label = "Ncf",
                    isError = true,
                    imeAction = ImeAction.Next
                )
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
                    }
                )
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
                    }
                )
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
                    onValueChange = {viewModel.fecha = it },
                    label = "Fecha",
                    isError = true,
                    imeAction = ImeAction.Next
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(onClick = {
            keyboardController?.hide()
            viewModel.saveGasto()
            viewModel.setMessageShown()
        }, modifier = Modifier.fillMaxWidth())
        {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Guardar")
            Text(text = "Guardar")
        }

        if (gastos != null) {
            Consult(gastos = gastos)
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

