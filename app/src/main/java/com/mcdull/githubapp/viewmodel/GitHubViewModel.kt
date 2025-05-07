package com.mcdull.githubapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcdull.githubapp.model.Repository
import com.mcdull.githubapp.network.RetrofitClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class GitHubViewModel : ViewModel() {
    companion object {
        private const val TAG = "ViewModel"
    }

    // UI 状态
    sealed interface UiState {
        object Loading : UiState
        data class Success(val repositories: List<Repository>) : UiState
        data class Error(val message: String) : UiState
    }

    private val _uiState = MutableLiveData<UiState>(UiState.Loading)
    val uiState: LiveData<UiState> = _uiState

    fun test() {
        viewModelScope.launch {
            try {
                // 计算7天前的日期
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val sevenDaysAgo = dateFormat.format(calendar.time)

                val data = RetrofitClient.apiService.searchRecentJavaRepositories(
                    queries = listOf("language:java", "created:>${sevenDaysAgo}")
                )
                Log.d(TAG, "test() returned: $data")
            } catch (e: Exception) {
                Log.e(TAG, "test: error", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}