package com.example.joseph_p2_ap2.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.joseph_p2_ap2.data.remote.GastoApi
import com.example.joseph_p2_ap2.data.remote.dto.GastoDTO
import com.example.joseph_p2_ap2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class GastosRepository @Inject constructor(private val api: GastoApi) {
    fun getGastos(): Flow<Resource<List<GastoDTO>>> = flow {
        try {
            emit(Resource.Loading())

            val gasto = api.getGastos()

            emit(Resource.Success(gasto))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }


    fun getGastoId(id: Int): Flow<Resource<GastoDTO>> = flow {
        try {
            emit(Resource.Loading())

            val gasto =
                api.getGastoById(id)

            emit(Resource.Success(gasto))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    suspend fun postGasto(gasto: GastoDTO) = api.postGasto(gasto)
    suspend fun deleteGasto(id: Int) : GastoDTO? {
        return api.deleteGasto(id).body()
    }
    suspend fun putGasto(id:Int, gasto: GastoDTO) : GastoDTO?{
        api.putGasto(id = id, gasto = gasto)
        return gasto
    }
}