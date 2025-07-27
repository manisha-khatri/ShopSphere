package com.example.shopsphere.ui

sealed class UIState<out T> {
    object Loading: UIState<Nothing>()
    data class Success<T>(val data: T): UIState<T>()
    class Failure(val message: String): UIState<Nothing>()
}