package com.training.eshopper.data.repository

import com.training.eshopper.common.Result
import com.training.eshopper.data.remote.ApiService
import org.json.JSONObject
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun login(email: String, password: String): Result<String> {
        val response = apiService.login(email, password)
        return try {
            val jsonObject = JSONObject(response.string())
            if (jsonObject.has("success")) {
                Result.Success(jsonObject.getString("token"))
            } else {
                Result.Error(Exception("Authentication failed"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}