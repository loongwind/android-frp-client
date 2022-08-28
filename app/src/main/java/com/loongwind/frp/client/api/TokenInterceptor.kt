package com.loongwind.frp.client.api

import android.util.Base64
import com.loongwind.frp.client.constant.DEFAULT_ADMIN_PWD
import com.loongwind.frp.client.constant.DEFAULT_ADMIN_USER
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val auth = "$DEFAULT_ADMIN_USER:$DEFAULT_ADMIN_PWD"
        val base64Auth = Base64.encodeToString(auth.toByteArray(), Base64.DEFAULT).replace("\n", "")

        val newRequest = request.newBuilder()
            .header("Authorization", "Basic $base64Auth")
            .build()
        return chain.proceed(newRequest)
    }
}