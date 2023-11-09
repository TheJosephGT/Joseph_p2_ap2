package com.example.joseph_p2_ap2.data.remote.dto

data class GastoDTO (
    val idGasto: Int?=null,
    var fecha: String="",
    var idSuplidor: Int? = null,
    var suplidor: String="",
    var ncf: String="",
    var concepto: String="",
    var itbis:Int?=null,
    var monto: Int?=null
)