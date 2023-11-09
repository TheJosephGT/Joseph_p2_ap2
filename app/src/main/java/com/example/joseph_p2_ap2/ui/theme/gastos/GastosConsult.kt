package com.example.joseph_p2_ap2.ui.theme.gastos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.joseph_p2_ap2.data.remote.dto.GastoDTO

@Composable
fun Consult(gastos: List<GastoDTO>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(gastos) { gasto ->
                GastoItem(gasto)
            }
        }
    }
}

@Composable
fun GastoItem(gasto: GastoDTO, viewModel: GastosViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "ID: " + gasto.idGasto, style = MaterialTheme.typography.titleMedium)
            Text(text = gasto.fecha, style = MaterialTheme.typography.titleMedium)
            Text(text = gasto.suplidor, style = MaterialTheme.typography.titleMedium)
            Text(text = gasto.concepto, style = MaterialTheme.typography.titleMedium)
            Text(text = "NCF:" + gasto.ncf, style = MaterialTheme.typography.titleMedium)
            Text(text = "Itbis: " + gasto.itbis.toString(), style = MaterialTheme.typography.titleMedium)
            Text(text = gasto.monto.toString(), style = MaterialTheme.typography.titleMedium)

        }
    }
}