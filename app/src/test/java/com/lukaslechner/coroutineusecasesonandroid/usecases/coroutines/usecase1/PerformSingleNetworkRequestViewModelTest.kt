package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.ViewModelTestBaseClass
import org.junit.Assert
import org.junit.Test

class PerformSingleNetworkRequestViewModelTest : ViewModelTestBaseClass() {

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `should return Success - When network request is successful`() {

        //Given

        val fakeApi = FakeSuccessApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeApi)

        observeViewModel(viewModel)

        //When
        viewModel.performSingleNetworkRequest()

        //Then
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockAndroidVersions)
            ),
            receivedUiStates
        )
    }

    @Test
    fun `should return Error - when network request fails`() {

        //Given
        val viewModel = PerformSingleNetworkRequestViewModel(FakeErrorApi())
        observeViewModel(viewModel)

        //When
        viewModel.performSingleNetworkRequest()

        //Then
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Network request failed.")
            ),
            receivedUiStates
        )
    }

    private fun observeViewModel(viewModel: PerformSingleNetworkRequestViewModel) {
        viewModel.uiState().observeForever { uiState ->
            uiState?.let {
                receivedUiStates.add(uiState)
            }
        }
    }
}