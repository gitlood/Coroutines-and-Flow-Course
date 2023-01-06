package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.utils.ViewModelTestBaseClass
import org.junit.Assert
import org.junit.Test

class Perform2SequentialNetworkRequestsViewModelTest : ViewModelTestBaseClass() {

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `should return Success - when network request is Successful`() {
        //Given
        val viewModel = Perform2SequentialNetworkRequestsViewModel(FakeSuccessApi())
        observeViewModel(viewModel)

        //When
        viewModel.perform2SequentialNetworkRequest()

        //Then
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockVersionFeaturesAndroid10),
            ),
            receivedUiStates
        )
    }

    @Test
    fun `should return Error - when network request is fails`() {
        //Given
        val viewModel = Perform2SequentialNetworkRequestsViewModel(FakeErrorApi())
        observeViewModel(viewModel)

        //When
        viewModel.perform2SequentialNetworkRequest()

        //Then
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Network request failed!"),
            ),
            receivedUiStates
        )
    }

    private fun observeViewModel(viewModel: Perform2SequentialNetworkRequestsViewModel) {
        viewModel.uiState().observeForever { uiState ->
            uiState?.let {
                receivedUiStates.add(uiState)
            }
        }
    }
}