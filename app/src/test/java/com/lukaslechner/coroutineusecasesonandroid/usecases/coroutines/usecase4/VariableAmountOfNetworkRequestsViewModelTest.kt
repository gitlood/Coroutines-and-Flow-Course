package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4

import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import com.lukaslechner.coroutineusecasesonandroid.utils.ViewModelTestBaseClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class VariableAmountOfNetworkRequestsViewModelTest : ViewModelTestBaseClass() {

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performNetworkRequestsSequentially() should return Success UiState on successful network requests after 4000ms`() =
        runTest {
            val responseDelay = 1000L
            val fakeApi = FakeSuccessApi(responseDelay)
            val viewModel = VariableAmountOfNetworkRequestsViewModel(fakeApi)
            viewModel.observe()

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequestsSequentially()

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(
                        listOf(
                            mockVersionFeaturesOreo,
                            mockVersionFeaturesPie,
                            mockVersionFeaturesAndroid10
                        )
                    )
                ),
                receivedUiStates
            )

            Assert.assertEquals(
                4000,
                currentTime
            )
        }

    @Test
    fun `performNetworkRequestsSequentially() should return Error UiState on unsuccessful recent-android-versions network request`() =
        runTest {
            val responseDelay = 1000L
            val fakeApi = FakeVersionsErrorApi(responseDelay)
            val viewModel = VariableAmountOfNetworkRequestsViewModel(fakeApi)
            viewModel.observe()

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequestsSequentially()

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Network Request Failed")
                ),
                receivedUiStates
            )
        }

    @Test
    fun `performNetworkRequestsSequentially() should return Error UiState on unsuccessful android-version-features network request`() =
        runTest {
            val responseDelay = 1000L
            val fakeApi = FakeFeaturesErrorApi(responseDelay)
            val viewModel = VariableAmountOfNetworkRequestsViewModel(fakeApi)
            viewModel.observe()

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequestsSequentially()

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Network Request Failed")
                ),
                receivedUiStates
            )
        }

    @Test
    fun `performNetworkRequestsConcurrently() should return Success UiState on successful network requests after 2000ms`() =
        runTest {
            val responseDelay = 1000L
            val fakeApi = FakeSuccessApi(responseDelay)
            val viewModel = VariableAmountOfNetworkRequestsViewModel(fakeApi)
            viewModel.observe()

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequestsConcurrently()

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(
                        listOf(
                            mockVersionFeaturesOreo,
                            mockVersionFeaturesPie,
                            mockVersionFeaturesAndroid10
                        )
                    )
                ),
                receivedUiStates
            )

            Assert.assertEquals(
                2000,
                currentTime
            )
        }

    @Test
    fun `performNetworkRequestsConcurrently() should return Error UiState on unsuccessful recent-android-versions network request`() =
        runTest {
            val responseDelay = 1000L
            val fakeApi = FakeVersionsErrorApi(responseDelay)
            val viewModel = VariableAmountOfNetworkRequestsViewModel(fakeApi)
            viewModel.observe()

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequestsConcurrently()

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Network Request Failed")
                ),
                receivedUiStates
            )
        }

    @Test
    fun `performNetworkRequestsConcurrently() should return Error UiState on unsuccessful android-version-features network request`() =
        runTest {
            val responseDelay = 1000L
            val fakeApi = FakeFeaturesErrorApi(responseDelay)
            val viewModel = VariableAmountOfNetworkRequestsViewModel(fakeApi)
            viewModel.observe()

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performNetworkRequestsConcurrently()

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Network Request Failed")
                ),
                receivedUiStates
            )
        }

    private fun VariableAmountOfNetworkRequestsViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}