package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.ViewModelTestBaseClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class NetworkRequestWithTimeoutViewModelTest : ViewModelTestBaseClass() {

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performNetworkRequest() should return Success UiState on successful network request within timeout`() =
        runTest {
            val responseDelay = 1000L
            val timeout = 1001L
            val fakeApi = FakeSuccessApi(responseDelay)
            val viewModel = NetworkRequestWithTimeoutViewModel(fakeApi)
            viewModel.observe()

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequest(timeout)

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(mockAndroidVersions)
                ),
                receivedUiStates
            )
        }

    @Test
    fun `performNetworkRequest() should return Error UiState with timeout error message if timeout gets exceeded`() =
        runTest {
            val responseDelay = 1000L
            val timeout = 999L
            val fakeApi = FakeSuccessApi(responseDelay)
            val viewModel = NetworkRequestWithTimeoutViewModel(fakeApi)
            viewModel.observe()

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequest(timeout)

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Network request failed.")
                ),
                receivedUiStates
            )
        }

    @Test
    fun `performNetworkRequest() should return Error UiState on unsuccessful network response`() =
        runTest {
            val responseDelay = 1000L
            val timeout = 1001L
            val fakeApi = FakeVersionsErrorApi(responseDelay)
            val viewModel = NetworkRequestWithTimeoutViewModel(fakeApi)
            viewModel.observe()

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequest(timeout)

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Network request failed.")
                ),
                receivedUiStates
            )
        }

    private fun NetworkRequestWithTimeoutViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}