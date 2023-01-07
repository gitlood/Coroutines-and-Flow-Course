package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*

suspend fun main(): Unit = coroutineScope {

    val stockFlow = stocksFlow()

    stockFlow
        .onCompletion { cause ->
            if (cause == null) {
                println("Flow successfully completed")
            } else {
                println("Flow completed exceptionally with $cause")
            }
        }
        .onEach { stock ->
            throw Exception("Exception in collect{}")
            println("Collected $stock")
        }
        //collect only catches exception in upstream
        .catch { throwable ->
            println("Handle exception in catch() operator $throwable")
        }
        .launchIn(this)
}


private fun stocksFlow(): Flow<String> = flow {
    emit("Apple")
    emit("Microsoft")

    throw Exception("Network Request Failed")
}
