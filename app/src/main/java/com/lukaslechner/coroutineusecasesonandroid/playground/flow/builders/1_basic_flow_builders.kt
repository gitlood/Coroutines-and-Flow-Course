package com.lukaslechner.coroutineusecasesonandroid.playground.flow.builders

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

suspend fun main() {

    val firstFlow = flowOf(1).collect { emittedValue ->
        println("firstFlow: $emittedValue")
    }
    val secondFlow = flowOf(1, 2, 3)

    secondFlow.collect { emittedValue ->
        println("secondFlow $emittedValue")
    }

    listOf("A", "B", "C").asFlow().collect { emittedValue ->
        println("asFlow: $emittedValue")
    }

    flow {
        delay(2000)
        emit("item emitted after 2000ms")

        secondFlow.collect { emittedValue ->
            emit(emittedValue)
        }

        emitAll(secondFlow)
    }.collect { emittedValues ->
        println("flow{}: $emittedValues")
    }

}