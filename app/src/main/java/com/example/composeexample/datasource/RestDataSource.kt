package com.example.composeexample.datasource

import com.example.composeexample.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface RestDataSource {

    @Headers(
        "apikey:dlTYbPznrXdCq4593DD9SQEyW4zGvKUm"
    )
    @GET("latest")
    suspend fun getLatest(@Query("base") base: String = "USD"): Response
}
