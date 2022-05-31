package com.example.composeexample.model

sealed interface ResponseState {

    object Loading: ResponseState

    data class Data(val list: List<OneCoin>) : ResponseState

    data class Error(val e: Exception) : ResponseState
}
