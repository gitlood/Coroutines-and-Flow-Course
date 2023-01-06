package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

fun main() {
    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)

        println("Emitting second value")
        emit(2)
    }

    val scope = CoroutineScope(EmptyCoroutineContext)

    //concurrently
    //tidier - less indentation
    flow.onEach { item ->
        println("received $item in launchIn - 1")
    }.launchIn(scope)

    flow.onEach { item ->
        println("received $item in launchIn - 2")
    }.launchIn(scope)

    //sequentially
    scope.launch {
        flow.collect { item ->
            println("received $item in collect - 1")
        }
    }

    scope.launch {
        flow.collect { item ->
            println("received $item in collect - 2")
        }
    }

    Thread.sleep(1000)
}