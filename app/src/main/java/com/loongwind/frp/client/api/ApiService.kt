package com.loongwind.frp.client.api

import com.loongwind.frp.client.model.FrpcResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiService {

    @GET("api/status")
    suspend fun getFrpcStatus() : FrpcResponse
}