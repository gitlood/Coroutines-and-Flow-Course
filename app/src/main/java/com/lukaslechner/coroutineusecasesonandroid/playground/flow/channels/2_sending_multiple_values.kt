@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lukaslechner.coroutineusecasesonandroid.playground.flow.channels

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {

    val channel = produce {
        println("Sending 10")
        send(10)

        println("Sending 20")
        send(20)
    }

    launch {
        channel.consumeEach { receivedValue ->
            println("Consumer 1: $receivedValue")
        }
    }

    launch {
        channel.consumeEach { receivedValue ->
            println("Consumer 2: $receivedValue")
        }
    }
}