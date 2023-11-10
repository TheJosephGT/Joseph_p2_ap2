package com.example.joseph_p2_ap2.data.remote.dto

data class Suplidor(val id: Int, val nombre: String)

var suplidores = listOf(
    Suplidor(id = 1, nombre = "CLARO"),
    Suplidor(id = 2, nombre = "ALTICE"),
    Suplidor(id = 6, nombre = "CLARO DOMINICANA"),
    Suplidor(id = 7, nombre = "ALTICE DOMINICANA"),
    Suplidor(id = 8, nombre = "TELEOPERADORA DEL NORDESTE SRL"),
    Suplidor(id = 9, nombre = "VIEW COMUNICACIONES SRL")
)