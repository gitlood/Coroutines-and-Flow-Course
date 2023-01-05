package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {

    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)

    val passedJob = Job()
    val coroutineJob = scope.launch(passedJob) {
        println("Starting coroutine")
        delay(1000)
    }

    Thread.sleep(1000)

    println("passedJob and coroutineJob are references to the same object: ${passedJob === coroutineJob}")

    println("Is coroutine job a child of scopejob? => ${scopeJob.children.contains(coroutineJob)}")

}