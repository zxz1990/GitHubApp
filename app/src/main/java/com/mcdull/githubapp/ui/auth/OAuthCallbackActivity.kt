package com.mcdull.githubapp.ui.auth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.mcdull.githubapp.ui.profile.ProfileViewModel

class OAuthCallbackActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "OAuthCallbackActivity"
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        handleIntent(intent)
//        finish()
    }

    private fun handleIntent(intent: Intent?) {
        val uri: Uri? = intent?.data
        Log.i(TAG, "handleIntent: intent = $intent")
        uri?.getQueryParameter("code")?.let { code ->
            viewModel.handleAuthCallback(code)
        } ?: run {
            // 处理授权失败
        }
    }
}