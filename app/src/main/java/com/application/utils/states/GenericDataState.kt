package com.application.utils.states



sealed class GenericDataState<out Model> {
    data object Loading: GenericDataState<Nothing>()
    data class Result<Model>(val data: Model): GenericDataState<Model>()
    data class Error(val message: String, val code: Int?=-1): GenericDataState<Nothing>()
}