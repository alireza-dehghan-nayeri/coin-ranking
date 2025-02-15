package com.example.coinranking.core

import retrofit2.Response

suspend fun <ResponseType> asApiResponse(apiCall: suspend () -> Response<ResponseType>): ApiResponse<ResponseType> {
    return try {
        ApiResponse.create(apiCall())
    } catch (t: Throwable) {
        ApiResponse.create(t)
    }
}