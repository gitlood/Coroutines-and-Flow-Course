package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hot_and_cold_flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

fun main() {
    val sharedFlow = MutableSharedFlow<Int>()

    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch {
        repeat(5) { index ->
            println("SharedFlow emits $index")
            sharedFlow.emit(index)
            delay(200)
        }
    }

    scope.launch {
        sharedFlow.collect{
            println("Collected $it in collector 1")
        }
    }

 scope.launch {
        sharedFlow.collect{
            println("Collected $it in collector 2")
        }
    }

    Thread.sleep(1500)
}