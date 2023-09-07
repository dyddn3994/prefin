package com.prefin.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Child
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "LoginViewModel"
class LoginViewModel : ViewModel() {
    fun login(userId: String, pwd: String) {
        viewModelScope.launch {
            try {
                RetrofitUtil.loginApi.childLogin(Child(userId = userId, password = pwd))
            } catch (e: Exception) {
                Log.d(TAG, "login: 실패 $userId / $pwd")
            }
        }
    }
}
