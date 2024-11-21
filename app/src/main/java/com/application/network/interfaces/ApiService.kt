package com.luv.shup.network.interfaces

import com.application.data.model.StateList
import com.google.gson.JsonArray
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Query


interface ApiService {

    @GET("https://673f9d84a9bc276ec4b91c15.mockapi.io/idt/api/stateDetails")
    fun getStateList() : Call<List<StateList>>

    @GET("https://673f9d84a9bc276ec4b91c15.mockapi.io/idt/api/stateDetails")
    fun getStateDetailsByName(
        @Query("name") name: String,
    ) : Call<JsonArray>

}