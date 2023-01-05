package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(10) { index ->
            //ensureActive()
            //yield()
            if (isActive) {
                println("operation number $index")
                Thread.sleep(100)
            } else {
                withContext(NonCancellable) {
                    delay(100)
                    //clean up operations
                    println("Cleaning up...")
                    throw CancellationException()
                    //     return@launch
                }
            }
        }

    }

    delay(250)
    println("Cancelling Coroutine")
    job.cancel()
}