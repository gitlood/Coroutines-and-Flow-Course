package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        val stockFlow = stocksFlow().map {
            throw Exception("Exception in Map")
        }

        try {
            stockFlow
                .onCompletion { cause ->
                    if (cause == null) {
                        println("Flow successfully completed")
                    } else {
                        println("Flow completed exceptionally with $cause")
                    }
                }
                .collect { stock ->
                    println("Collected $stock")
                }
        }catch (e:Exception){
            println("Handle Exception in catch block")
        }
    }
}

private fun stocksFlow(): Flow<String> = flow {
    emit("Apple")
    emit("Microsoft")

    throw Exception("Network Request Failed")
}