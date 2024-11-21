package com.application.data.repositoryimpl

import com.application.data.model.StateList
import com.application.data.repository.StateListRepository
import com.google.gson.JsonArray
import com.luv.shup.network.clients.RetrofitClient
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class StateListRepositoryImpl : StateListRepository {

    override suspend fun getStateList(): List<StateList>? {
        return suspendCancellableCoroutine { continuation ->
            RetrofitClient.apiService.getStateList().enqueue(object : retrofit2.Callback<List<StateList>> {
                override fun onResponse(call: Call<List<StateList>>, response: Response<List<StateList>>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body())
                    } else {
                        continuation.resumeWithException(Exception("Error: ${response.errorBody()?.string()}"))
                    }
                }

                override fun onFailure(call: Call<List<StateList>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    override suspend fun getStateDetailsByName(stateName: String): JsonArray {
        return suspendCancellableCoroutine { continuation ->
            RetrofitClient.apiService.getStateDetailsByName(stateName)
                .enqueue(object : retrofit2.Callback<JsonArray> {
                override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                    if (response.isSuccessful) {
                        if(response.body() != null) {
                            continuation.resume(response.body()!!)
                        }else{
                            continuation.resume(response.body()?:JsonArray())
                        }
                    } else {
                        continuation.resumeWithException(Exception("Error: ${response.errorBody()?.string()}"))
                    }
                }

                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}