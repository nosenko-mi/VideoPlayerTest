package com.nmi.videotest.data.remote

import android.util.Log
import com.nmi.videotest.common.result.NetworkError
import com.nmi.videotest.common.result.Result
import com.nmi.videotest.data.remote.dto.VideoDto
import com.nmi.videotest.data.remote.dto.toModel
import com.nmi.videotest.domain.model.VideoModel
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException

class PexelVideoRemoteDatasource(
    private val api: PexelVideoApi
) {

    suspend fun getVideoById(id: Long): Result<VideoDto, NetworkError>{
        val response = try {
            api.getVideoById(id)
        }catch (e: UnresolvedAddressException){
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERVER_ERROR)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UNKNOWN)
        }
        return when(response.code()){
            in 200..299 -> {
                val video = response.body()!!
                Result.Success(video)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getVideos(page: Int, perPage: Int): Result<List<VideoDto>, NetworkError>{
        val response = try {
            api.getVideos(page, perPage)
        }catch (e: UnresolvedAddressException){
            Log.e("Datasource", e.message.toString())
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            Log.e("Datasource", e.message.toString())
            return Result.Error(NetworkError.SERVER_ERROR)
        } catch (e: Exception) {
            Log.e("Datasource", e.message.toString())
            return Result.Error(NetworkError.UNKNOWN)
        }
        Log.e("Datasource", "response code: ${response.code()}")
        Log.e("Datasource", "response body: ${response.message()}")
        return when(response.code()){
            in 200..299 -> {
                val videos = response.body()!!.videos
                Result.Success(videos)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

}