package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class RoomAndCoroutinesViewModel(
    private val api: MockApi,
    private val database: AndroidVersionDao
) : BaseViewModel<UiState>() {

    fun loadData() {
        uiState.value = UiState.Loading.LoadFromDb

        viewModelScope.launch {
            val localVersions = database.getAndroidVersions()
            if (localVersions.isEmpty()) {
                uiState.value = UiState.Error(DataSource.DATABASE, "Database is empty")
            } else {
                uiState.value =
                    UiState.Success(DataSource.DATABASE, localVersions.mapToUiModelList())
            }

            uiState.value = UiState.Loading.LoadFromNetwork
            try {
                val recentVersions = api.getRecentAndroidVersions()
                recentVersions.forEach { androidVersion ->
                    database.insert(androidVersion.mapToEntity())
                }
                uiState.value = UiState.Success(DataSource.NETWORK, recentVersions)
            } catch (exception: Exception) {
                Timber.e(exception)
                uiState.value = UiState.Error(DataSource.NETWORK, "Something went wrong")
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            database.clear()
        }
    }
}

enum class DataSource {
    DATABASE,
    NETWORK
}