package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.*

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    //because await is being called the parent level coroutine will catch the exception

//    val deferred = scope.async {
//        delay(200)
//
//        throw RuntimeException()
//    }
//
//    scope.launch {
//        deferred.await()
//    }

    //launch will throw exception as async throws an exception that stops async coroutine
    scope.launch {
        async {
            delay(200)
            throw RuntimeException()
        }
    }

    Thread.sleep(500)
}