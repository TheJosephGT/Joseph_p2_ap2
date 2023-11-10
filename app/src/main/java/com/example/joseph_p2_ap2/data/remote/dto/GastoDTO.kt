package com.example.joseph_p2_ap2.data.remote.dto

import androidx.room.PrimaryKey

data class GastoDTO (
    val idGasto: Int?=null,
    var fecha: String = "",
    var ncf: String = "",
    var idSuplidor: Int= 0,
    var suplidor:String = "",
    var concepto: String = "",
    var descuento: Int?= 0,
    var itbis: Int?= 0,
    var monto: Int?= 0,
)