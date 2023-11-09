package com.example.joseph_p2_ap2.ui.theme.gastos

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalComposeUiApi::class)
@Preview(showSystemUi = true)
@Composable
fun GastosScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Registro de gastos", style = MaterialTheme.typography.titleLarge)
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomOutlinedTextField(
                    value = "",
                    onValueChange = { },
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
                    value = "",
                    onValueChange = { },
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
                CustomOutlinedTextField(
                    value = "",
                    onValueChange = { },
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
                CustomOutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = "Itbis",
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
                CustomOutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = "Monto",
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
                    value = "",
                    onValueChange = { },
                    label = "Fecha",
                    isError = true,
                    imeAction = ImeAction.Next
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(onClick = {
            keyboardController?.hide()
        }, modifier = Modifier.fillMaxWidth())
        {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Guardar")
            Text(text = "Guardar")
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