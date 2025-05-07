package com.mcdull.githubapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcdull.githubapp.model.Repository
import com.mcdull.githubapp.network.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class LoadingState {
    object LOADING : LoadingState()
    object SUCCESS : LoadingState()
    data class ERROR(val message: String) : LoadingState()
}

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {
    private val _searchResults = MutableLiveData<List<Repository>>()
    val searchResults: LiveData<List<Repository>> = _searchResults

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState> = _loadingState

    fun searchRepositories(query: String, language: String) {
        viewModelScope.launch {
            _loadingState.value = LoadingState.LOADING
            try {
                val response =
                    RetrofitClient.apiService.searchRepositories(queries = listOf("$query language:$language"))
                _searchResults.value = response.items
                _loadingState.value = LoadingState.SUCCESS
            } catch (e: Exception) {
                _loadingState.value = LoadingState.ERROR(e.message ?: "搜索失败")
            }
        }
    }
}