package com.training.eshopper.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): ResponseBody

    @GET("product")
    suspend fun getAllProducts(): ResponseBody
}