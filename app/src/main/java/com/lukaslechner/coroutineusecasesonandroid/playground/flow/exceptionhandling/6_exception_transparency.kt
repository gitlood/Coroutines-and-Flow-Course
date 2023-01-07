package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {

    flow {
        emit(1)
    }.catch {
        println("Catch exception in flow builder.")
    }.collect { emittedValue ->
        throw Exception("Exception in collect{}")
    }
}