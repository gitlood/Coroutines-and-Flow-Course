package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.*


fun main() {


    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable in CoroutineExceptionHandler!")
    }

    //this is inherited by all children of supervisor scope
    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
        try {
            doSomethingSuspend()
        } catch (e: Exception) {
            println("Caught $e")
        }
    }

    Thread.sleep(100)
}

private suspend fun doSomethingSuspend() {
    //supervisor scope does not rethrow exceptions of its child coroutines
    //supervisor will not propagate exception up the coroutine hierarchy
    // if exception is thrown in supervisor scope all children are cancelled
    // launch does propagate up the hierarchy
    supervisorScope {
        launch {
            println("CEH: ${coroutineContext[CoroutineExceptionHandler]}")
            throw RuntimeException()
        }
    }
}