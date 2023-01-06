package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10

import com.lukaslechner.coroutineusecasesonandroid.utils.ViewModelTestBaseClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalculationInBackgroundViewModelTest : ViewModelTestBaseClass() {

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performCalculation() should perform correct calculations`() = runTest {
        val viewModel =
            CalculationInBackgroundViewModel(mainDispatcherRule.testDispatcher).apply {
                observe()
            }

        Assert.assertTrue(receivedUiStates.isEmpty())

        viewModel.performCalculation(1)
        runCurrent()

        Assert.assertEquals(
            UiState.Loading,
            receivedUiStates.first()
        )

        Assert.assertEquals(
            "1",
            (receivedUiStates[1] as UiState.Success).result
        )

        receivedUiStates.clear()

        viewModel.performCalculation(2)
        runCurrent()

        Assert.assertEquals(
            UiState.Loading,
            receivedUiStates.first()
        )

        Assert.assertEquals(
            "2",
            (receivedUiStates[1] as UiState.Success).result
        )

        receivedUiStates.clear()

        viewModel.performCalculation(3)
        runCurrent()

        Assert.assertEquals(
            UiState.Loading,
            receivedUiStates.first()
        )

        Assert.assertEquals(
            "6",
            (receivedUiStates[1] as UiState.Success).result
        )

    }

    private fun CalculationInBackgroundViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}
