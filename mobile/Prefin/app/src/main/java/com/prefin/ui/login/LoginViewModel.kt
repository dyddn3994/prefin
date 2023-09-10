package com.prefin.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped
import com.prefin.config.ApplicationClass
import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "LoginViewModel"
class LoginViewModel : ViewModel() {
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess : LiveData<Boolean> get() = _loginSuccess
    fun childLogin(userId: String, pwd: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitUtil.loginApi.childLogin(Child(userId = userId, password = pwd))
                if(response != null){
                    Log.d(TAG, "유저 정보: $response")
                    // 로그아웃 구현하면 다시 주석 풀기
//                    ApplicationClass.sharedPreferences.addChildUser(response)
                    _loginSuccess.value = true

                }

            } catch (e: Exception) {
                Log.d(TAG, "login: 실패 $userId / $pwd")
            }
        }
    }

    fun parentLogin(userId: String, pwd: String) {
        var parentUser = Parent()
        parentUser.userId = userId
        parentUser.password = pwd
        viewModelScope.launch {
            try {
                val response = RetrofitUtil.loginApi.parentLogin(parentUser)
                if(response != null){
                    Log.d(TAG, "유저 정보: $response")
                    //로그아웃 구현하면 다시 주석 풀어야함
//                    ApplicationClass.sharedPreferences.addParentUser(response)
                    _loginSuccess.value = true

                }
            } catch (e: Exception) {

                Log.d(TAG, "login: 실패 $userId / $pwd")
                Log.d(TAG, "원인 : ${e.message}")
            }
        }
    }
}