package com.nabilmh.seekmax.utils

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

suspend fun <T : Any> graphApiCall(call: () -> ApolloCall<T>): com.apollographql.apollo.api.Response<T> {
    val graphCall: ApolloCall<T> = call()
    return try {
        graphCall.await()
    } catch (t: Throwable) {
        val operation = graphCall.operation()

        com.apollographql.apollo.api.Response.builder<T>(operation)
            .errors(listOf(com.apollographql.apollo.api.Error(t.message ?: "Error"))).build()
    }
}

suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): ApiResponse<T> {
    val response: Response<T>
    try {
        response = call.invoke()
    } catch (t: Throwable) {
        return ApiResponse.Failure(t.message ?: t.toString(), 400)
    }

    try {
        if (response.isSuccessful) {
            response.body()?.let { data ->
                return ApiResponse.Success(data)
            }
        } else {
            response.errorBody()?.let { error ->
                error.close()
                return ApiResponse.Failure(error.string(), response.code())
            }
        }
    } catch (e: Exception) {
        return ApiResponse.Failure(e.message ?: e.toString(), 400)
    }

    return ApiResponse.Failure("Unknown Error", 400)
}

fun <T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    withTimeoutOrNull(20000L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiResponse.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    error.close()
                    emit(ApiResponse.Failure(error.toString(), response.code()))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e.message ?: e.toString(), 400))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again.", 408))
}.flowOn(Dispatchers.IO)