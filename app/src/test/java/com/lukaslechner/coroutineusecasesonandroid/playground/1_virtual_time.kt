package com.lukaslechner.coroutineusecasesonandroid.playground

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SystemUnderTest {
    suspend fun functionWithDelay(): Int {
        delay(1000)
        return 42
    }
}

fun CoroutineScope.functionThatStartsNewCoroutine() {
    launch {
        delay(1000)
        println("Coroutine completed!")
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class TestClass {

    @Test
    fun `functionWithDelay() - should return 42`() = runTest {

        val realTimeStart = System.currentTimeMillis()
        val virtualTimeStart = currentTime

        functionThatStartsNewCoroutine()
        advanceTimeBy(1000)

//        //Given
//        val sut = SystemUnderTest()
//
//        //When
//        val actual = sut.functionWithDelay()
//
//        //Then
//        Assert.assertEquals(42, actual)

        val realTimeDuration = System.currentTimeMillis() - realTimeStart
        val virtualTimeDuration = currentTime - virtualTimeStart

        println("Test took $realTimeDuration real ms")
        println("Test took $virtualTimeDuration virtual ms")
    }
}