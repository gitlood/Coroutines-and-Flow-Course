package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class FlowUseCase1ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    val currentStockPriceAsLiveData: MutableLiveData<UiState> = MutableLiveData()

    init {
//        viewModelScope.launch {
//            stockPriceDataSource.latestStockList.collect { stockList ->
//                currentStockPriceAsLiveData.value = UiState.Success(stockList)
//            }
//        }

        //less nesting using launchin
        stockPriceDataSource
            .latestStockList
            .map { listOfStocks ->
                UiState.Success(listOfStocks) as UiState
            }
            .onStart {
                emit(UiState.Loading)
                //  Timber.d("Flow starts to be collected 1")
            }
            //above this is the upstream completion block
            //below completion block is downstream
//            .onCompletion { cause ->
//                Timber.d("Flow starts has completed!.")
//                //emit affect downstream
//                emit(emptyList())
//            }
            .onEach { UiState ->
                currentStockPriceAsLiveData.value = UiState
            }
//            .onStart {
//                Timber.d("Flow starts to be collected 2")
//            }
            .launchIn(viewModelScope)
    }
}