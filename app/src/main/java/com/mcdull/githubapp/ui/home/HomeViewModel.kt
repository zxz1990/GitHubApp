package com.mcdull.githubapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcdull.githubapp.model.GitHubSearchResponse
import com.mcdull.githubapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class RepositoryItem(
    val name: String
    // 可根据实际情况添加更多属性
)

class HomeViewModel : ViewModel() {
    private val _repositories = MutableLiveData<GitHubSearchResponse?>(null)
    val repositories: LiveData<GitHubSearchResponse?> = _repositories

    init {
        fetchRepositories()
    }

    private fun fetchRepositories() {
        viewModelScope.launch {
            try {
                // 计算7天前的日期
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val sevenDaysAgo = dateFormat.format(calendar.time)

                val result = RetrofitClient.apiService.searchRecentJavaRepositories(
                    queries = listOf("language:java", "created:>${sevenDaysAgo}")
                )
                _repositories.value = result
            } catch (e: Exception) {
                // 处理异常
            }
        }
    }
}