package com.lukaslechner.coroutineusecasesonandroid.playground.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

suspend fun main(): Unit = coroutineScope {

    val flow = MutableSharedFlow<Int>(extraBufferCapacity = 10)

    //Collector 1
    launch {
        flow.collect {
            println("Collector 1 process $it")
        }
    }

    //Collector 2
    launch {
        flow.collect {
            println("Collector 2 process $it")
            delay(100)
        }
    }

    //Emitter
    launch {
        val timeToEmit = measureTimeMillis {
            repeat(5) {
                flow.emit(it)
                delay(10)
            }
        }
        println("Time to emit all values: $timeToEmit ms")
    }
}
