package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import com.lukaslechner.coroutineusecasesonandroid.utils.ViewModelTestBaseClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PerformNetworkRequestsConcurrentlyViewModelTest : ViewModelTestBaseClass() {
    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performNetworkRequestsSequentially - should load data sequentially`() =
        runTest(mainDispatcherRule.testDispatcher) {

            //Given
            val responseDelay = 1000L
            val fakeSuccessApi = FakeSuccessApi(responseDelay)
            val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeSuccessApi)
            observeViewModel(viewModel)

            //When
            viewModel.performNetworkRequestsSequentially()
            advanceUntilIdle()

            //Then
            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(
                        listOf(
                            mockVersionFeaturesOreo,
                            mockVersionFeaturesPie,
                            mockVersionFeaturesAndroid10
                        )
                    ),
                ),
                receivedUiStates
            )
        }

    @Test
    fun `performNetworkRequestsConcurrently - should load data concurrently`() =
        runTest(mainDispatcherRule.testDispatcher) {

            //Given
            val responseDelay = 1000L
            val fakeSuccessApi = FakeSuccessApi(responseDelay)
            val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeSuccessApi)
            observeViewModel(viewModel)

            //When
            viewModel.performNetworkRequestsConcurrently()
            advanceUntilIdle()

            //Then
            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(
                        listOf(
                            mockVersionFeaturesOreo,
                            mockVersionFeaturesPie,
                            mockVersionFeaturesAndroid10
                        )
                    ),
                ),
                receivedUiStates
            )
        }

    private fun observeViewModel(viewModel: PerformNetworkRequestsConcurrentlyViewModel) {
        viewModel.uiState().observeForever { uiState ->
            uiState?.let {
                receivedUiStates.add(uiState)
            }
        }
    }
}