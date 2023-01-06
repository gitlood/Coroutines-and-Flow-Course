package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        //.filterNot { it > 3 }
        //.filterNotNull
        //.filterIsInstance<Int>
        .filter { it > 3 }
        .collect { collectedValue ->
            println(collectedValue)
        }
}