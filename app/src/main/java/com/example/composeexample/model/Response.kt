package com.example.composeexample.model

import com.example.composeexample.model.Rates
import com.google.gson.annotations.SerializedName


data class Response(
    @SerializedName("base") var base: String,
    @SerializedName("date") var date: String,
    @SerializedName("rates") var rates: Rates = Rates(),
    @SerializedName("success") var success: Boolean,
    @SerializedName("timestamp") var timestamp: Int
)