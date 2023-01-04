package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()

    val deferred1 = async(start = CoroutineStart.LAZY) {
        val result1 = networkCall(1)
        println("results received: $result1 after ${elapsedMillis(startTime)}")
        result1
    }
    val deferred2 = async(start = CoroutineStart.LAZY) {
        val result2 = networkCall(2)
        println("results received: $result2 after ${elapsedMillis(startTime)}")
        result2
    }
    deferred1.start()
    deferred2.start()
    val resultList = listOf(deferred1.await(), deferred2.await())

    println("Result list: $resultList after ${elapsedMillis(startTime)}ms")

}

suspend fun networkCall(number: Int): String {
    delay(500)
    return "Result $number"
}

fun elapsedMillis(startTime: Long) = System.currentTimeMillis() - startTime