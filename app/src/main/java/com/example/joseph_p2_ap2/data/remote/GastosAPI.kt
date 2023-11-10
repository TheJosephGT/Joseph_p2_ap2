package com.example.joseph_p2_ap2.data.remote

import com.example.joseph_p2_ap2.data.remote.dto.GastoDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GastoApi {
    @GET("api/Gastos")
    suspend fun getGastos():List<GastoDTO>

    @GET("api/Gastos/{id}")
    suspend fun getGastoById(@Path("id") id: Int): GastoDTO

    @POST("api/Gastos")
    suspend fun postGasto(@Body gasto: GastoDTO) : Response<GastoDTO>

    @PUT("api/Gastos/{id}")
    suspend fun putGasto(@Path("id") id:Int, @Body gasto: GastoDTO): Response<Unit>

    @DELETE("api/Gastos/{id}")
    suspend fun deleteGasto(@Path("id") id: Int): Response<GastoDTO>
}