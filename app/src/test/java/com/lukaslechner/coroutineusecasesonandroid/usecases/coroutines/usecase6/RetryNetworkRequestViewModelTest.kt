package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.ViewModelTestBaseClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class RetryNetworkRequestViewModelTest : ViewModelTestBaseClass() {

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performSingleNetworkRequest() should return Success UiState on successful network response`() =
        runTest {
            val responseDelay = 1000L
            val fakeApi = FakeSuccessApi(responseDelay)
            val viewModel = RetryNetworkRequestViewModel(fakeApi).apply {
                observe()
            }

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequest()
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
    fun `performSingleNetworkRequest() should retry network request two times`() =
        runTest {
            val responseDelay = 1000L
            val fakeApi = FakeSuccessOnThirdAttemptApi(responseDelay)
            val viewModel = RetryNetworkRequestViewModel(fakeApi).apply {
                observe()
            }

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequest()
            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(mockAndroidVersions)
                ),
                receivedUiStates
            )

            Assert.assertEquals(
                3,
                fakeApi.requestCount
            )

            // 3*1000 (Request delays) + 100 (initial delay) + 200 (second delay)
            Assert.assertEquals(
                3300,
                currentTime
            )
        }

    @Test
    fun `performSingleNetworkRequest() should return Error UiState on 3 unsuccessful network responses`() =
        runTest {
            val responseDelay = 1000L
            val fakeApi = FakeVersionsErrorApi(responseDelay)
            val viewModel = RetryNetworkRequestViewModel(fakeApi).apply {
                observe()
            }

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequest()
            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Network request failed.")
                ),
                receivedUiStates
            )

            Assert.assertEquals(
                3,
                fakeApi.requestCount
            )

            // 3*1000 response delays + 100 (initial delay) + 200 (second delay)
            Assert.assertEquals(
                3300,
                currentTime
            )
        }

    private fun RetryNetworkRequestViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}