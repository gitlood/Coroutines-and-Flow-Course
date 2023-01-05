package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase11

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CooperativeCancellationViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    var calculationJob: Job? = null

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        var result: BigInteger
        var resultString: String

        calculationJob = viewModelScope.launch {

            Timber.d("Coroutine Context: $coroutineContext")

            val computationDuration: Long = measureTimeMillis {
                result = calculateFactorialOf(factorialOf)
            }
            val stringConversionDuration: Long = measureTimeMillis {
                resultString = withContext(Dispatchers.IO) { result.toString() }
            }

            uiState.value =
                UiState.Success(resultString, computationDuration, stringConversionDuration)
        }
        calculationJob?.invokeOnCompletion { throwable ->
            if (throwable is CancellationException) {
                Timber.d("Calculation was cancelled!")
            }
        }

    }

    private suspend fun calculateFactorialOf(number: Int) = withContext(Dispatchers.IO) {
        var factorial = BigInteger.ONE
        for (i in 1..number) {
            yield()
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        Timber.d("Calculating Factorial Completed!")
        factorial
    }

    fun cancelCalculation() {
        calculationJob?.cancel()
    }

    fun uiState(): LiveData<UiState> = uiState

    private val uiState: MutableLiveData<UiState> = MutableLiveData()
}