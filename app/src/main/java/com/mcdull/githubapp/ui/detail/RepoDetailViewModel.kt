package com.mcdull.githubapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcdull.githubapp.model.ContentResponse
import com.mcdull.githubapp.model.Result
import com.mcdull.githubapp.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Retrofit


//@HiltViewModel
class RepoDetailViewModel constructor(
) : ViewModel() {
    companion object {
        private const val TAG = "RepoDetailViewModel"
    }

    private val _repoContent = MutableLiveData<Result<List<ContentResponse>>>()
    val repoContent: LiveData<Result<List<ContentResponse>>> = _repoContent

    fun loadRepoContent(owner: String, repo: String, path: String) {
        viewModelScope.launch {
            _repoContent.value = Result.loading()
            try {
                val response = RetrofitClient.apiService.getRepoContent(owner, repo, path)
                val sortedList = response.body()?.sortedWith(compareBy(
                    { it.type != "dir" },  // 目录优先
                    { it.name.toLowerCase() } // 名称排序
                ))
                _repoContent.value = Result.success(sortedList!!)
            } catch (e: Exception) {
                _repoContent.value = Result.error(e)
            }
        }
    }
}