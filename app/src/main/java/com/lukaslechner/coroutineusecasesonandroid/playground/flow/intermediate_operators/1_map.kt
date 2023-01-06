package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        //.mapNotNull { "Emission $it" }
        .map { "Emission $it" }
        .collect { collectedValue ->
            println(collectedValue)
        }
}