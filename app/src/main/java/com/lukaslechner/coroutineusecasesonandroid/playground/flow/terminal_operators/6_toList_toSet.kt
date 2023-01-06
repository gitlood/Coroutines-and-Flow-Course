package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet

suspend fun main() {
    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)

        println("Emitting second value")
        emit(2)
    }

    val item1 = flow.toSet()
    println("Received $item1")

    val item2 = flow.toList()
    println("Received $item2")
}