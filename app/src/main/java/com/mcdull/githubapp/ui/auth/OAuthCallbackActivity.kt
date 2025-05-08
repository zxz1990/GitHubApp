package com.mcdull.githubapp.ui.auth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mcdull.githubapp.MainActivity
import com.mcdull.githubapp.ui.profile.AuthState
import com.mcdull.githubapp.ui.profile.ProfileViewModel
import com.mcdull.githubapp.user.UserManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OAuthCallbackActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "OAuthCallbackActivity"
    }

    @Inject
    lateinit var userManager: UserManager

    private lateinit var viewModel: ProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

//        userManager.accessToken.observe(this) { token ->
//
//            if (token?.isNotEmpty() == true) {
//                Toast.makeText(this, "授权成功: $token", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "授权失败", Toast.LENGTH_LONG).show()
//            }
//            startActivity(Intent(this, MainActivity::class.java)) // 跳转回主界面
//        }

        viewModel.authState.observe(this) {
            if (it is AuthState.Success) {
                Toast.makeText(this, "授权成功", Toast.LENGTH_LONG).show()
                finish()
            } else if (it is AuthState.Error) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val uri: Uri? = intent?.data
        uri?.getQueryParameter("code")?.let { code ->
            viewModel.handleAuthCallback(code)
//            startActivity(Intent(this, MainActivity::class.java)) // 跳转回主界面
        } ?: run {
            // 处理授权失败
            Toast.makeText(this, "授权失败", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}