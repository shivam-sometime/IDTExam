package com.application.data.repository

import com.application.data.model.StateList
import com.google.gson.JsonArray
import org.json.JSONArray

interface StateListRepository {
    suspend fun getStateList(): List<StateList>?
    suspend fun getStateDetailsByName(stateName : String) : JsonArray
}