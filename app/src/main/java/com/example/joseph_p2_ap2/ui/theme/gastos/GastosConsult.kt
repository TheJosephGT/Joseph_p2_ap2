package com.example.joseph_p2_ap2.ui.theme.gastos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.joseph_p2_ap2.data.remote.dto.GastoDTO
import com.example.joseph_p2_ap2.util.Resource
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun Consult(gastos: List<GastoDTO>, onUpdate: (GastoDTO) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(gastos) { gasto ->
                GastoItem(gasto, onUpdate = onUpdate)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun GastoItem(
    gasto: GastoDTO,
    viewModel: GastosViewModel = hiltViewModel(),
    onUpdate: (GastoDTO) -> Unit,
) {
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
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Id: ${gasto.idGasto}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(2.8f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$formattedDate",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "${gasto.suplidor}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

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
                Spacer(modifier = Modifier.width(8.dp))
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()

        ) {
            Button(
                onClick = {onUpdate(gasto)},
                modifier = Modifier
                    .width(150.dp)
                    .padding(8.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.Black
                )
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Modificar"
                    )
                    Text(
                        text = "Modificar",
                        modifier = Modifier.padding(top = 3.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            OutlinedButton(
                onClick = {
                    gasto.idGasto?.let { viewModel.deleteGasto(it) }
                },
                modifier = Modifier.width(150.dp),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, Color.Red),
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
                Text(text = "Eliminar", color = Color.Red)
            }
        }
    }

}