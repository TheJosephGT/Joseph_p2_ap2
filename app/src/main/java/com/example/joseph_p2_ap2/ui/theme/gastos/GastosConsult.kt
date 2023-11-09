package com.example.joseph_p2_ap2.ui.theme.gastos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.joseph_p2_ap2.data.remote.dto.GastoDTO
import com.example.joseph_p2_ap2.util.Resource
import java.text.SimpleDateFormat
import java.util.Locale

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
    val originalFormat = "yyyy-MM-dd'T'HH:mm:ss"
    val newFormat = "yyyy-MM-dd"
    val dateString = gasto.fecha

    val originalDateFormatter = SimpleDateFormat(originalFormat, Locale.getDefault())
    val newDateFormatter = SimpleDateFormat(newFormat, Locale.getDefault())

    val parsedDate = originalDateFormatter.parse(dateString)
    val formattedDate = newDateFormatter.format(parsedDate)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Id: ${gasto.idGasto}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(3f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "$formattedDate",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("${gasto.suplidor}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, fontSize = 30.sp)

            Text(
                "${gasto.concepto}",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("NCF: ${gasto.ncf}", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "ITBIS: ${gasto.itbis}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "$${gasto.monto}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 25.sp
                )
            }
        }
        Divider(Modifier.padding(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {  }) {
                Text("Modificar")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Eliminar")
            }
        }
    }

}