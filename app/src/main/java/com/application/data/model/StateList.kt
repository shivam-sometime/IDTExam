package com.application.data.model

import com.google.gson.annotations.SerializedName

data class StateList(
    val name: String,
    val abbreviation: String,
    val capital: String,
    val population: Int,
    @SerializedName( "area_sq_miles") val areaSqMiles: Int,
    val region: String
)


