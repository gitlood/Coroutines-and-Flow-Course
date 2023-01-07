package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        val stockFlow = stocksFlow()

        stockFlow
            .onCompletion { cause ->
                if (cause == null) {
                    println("Flow successfully completed")
                } else {
                    println("Flow completed exceptionally with $cause")
                }
            }
            //collect only catches exception in upstream
            .catch { throwable ->
                println("Handle exception in catch() operator $throwable")
                emitAll(fallbackFlow())
            }
            .catch { throwable ->
                println("Handle exception in 2 catch() operator $throwable")
            }
            .collect { stock ->
                println("Collected $stock")
            }

    }
}

private fun stocksFlow(): Flow<String> = flow {
    emit("Apple")
    emit("Microsoft")

    throw Exception("Network Request Failed")
}

suspend fun fallbackFlow(): Flow<String> = flow {
    emit("Fallback Stock")

    throw Exception("Exception in Fallback Flow")
}