package com.mcdull.githubapp.ui.issue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcdull.githubapp.model.IssueResponse
import com.mcdull.githubapp.network.IssueRequest
import com.mcdull.githubapp.network.RetrofitClient
import com.mcdull.githubapp.user.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcdull.githubapp.model.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {
    companion object {
        private const val TAG = "IssueViewModel"
    }

    private val _issueResult = MutableLiveData<Result<IssueResponse>>()
    val issueResult: LiveData<Result<IssueResponse>> = _issueResult

    fun createIssue(owner: String, repo: String, title: String, body: String) {
        viewModelScope.launch {
            _issueResult.value = Result.loading()
            try {
                val response = RetrofitClient.apiService.raiseIssue(
                    "Bearer ${userManager.accessToken.value}",
                    owner,
                    repo,
                    IssueRequest(title, body)
                )
                Log.i(TAG, "createIssue: response = $response")
                _issueResult.value = Result.success(response.body()!!)
            } catch (e: Exception) {
                Log.e(TAG, "createIssue: error", e)
                _issueResult.value = Result.error(e)
            }
        }
    }
}