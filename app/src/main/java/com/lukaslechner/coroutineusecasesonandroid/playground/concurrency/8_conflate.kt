package com.lukaslechner.coroutineusecasesonandroid.playground.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {

    val flow = flow {
        repeat(5) {
            println("Emitter: Start Cooking Pancake $it")
            delay(100)
            println("Emitter: Pancake $it is ready!")
            emit(it)
        }
        //conflate is a shortcut for a .buffer(capacity = 1, bufferOverflow = BufferOverflow.DROPLATEST)
        //similar to stateflow, only latest item is emitted
    }.conflate()

    flow.collect {
        println("Collector: Start eating pancake $it")
        delay(300)
        println("Collector: Finished eating pancake $it")
    }
}
