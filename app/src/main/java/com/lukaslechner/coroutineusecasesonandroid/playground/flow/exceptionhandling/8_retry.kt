package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {

    launch {
        stocksFlow()
            .catch { throwable ->
                println("Handle exception in catch() operator $throwable")
            }
            .collect { stockData ->
                println("Collected $stockData")
            }
    }
}

private fun stocksFlow(): Flow<String> = flow {
    repeat(5) { index ->

        delay(1000)

        if (index < 4) {
            emit("New Stock data")
        } else {
            throw NetworkException("Network Request Failed!")
        }
    }
//}.retry(retries = 3) { cause ->
//    println("Enter retry() with $cause")
//    delay(1000)
//    cause is NetworkException
//}
}.retryWhen { cause, attempt ->
    println("Enter retry() with $cause")
    delay(1000 * attempt)
    cause is NetworkException
}
class NetworkException(message: String) : Exception(message)