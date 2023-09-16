package com.prefin.ui.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.config.ApplicationClass
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "Prefin_SettingFragmentViewMode"
class SettingFragmentViewModel : ViewModel() {
    private fun parentLogout(parentId: Long) {
        viewModelScope.launch {
            try {
                RetrofitUtil.loginApi.parentLogout(parentId)
            } catch (e: Exception) {
                Log.d(TAG, "parentLogout: 부모 로그아웃 실패, ${e.message}")
            }
        }
    }

    private fun childLogout(childId: Long) {
        viewModelScope.launch {
            try {
                RetrofitUtil.loginApi.childLogout(childId)
            } catch (e: Exception) {
                Log.d(TAG, "childLogout: 자녀 로그아웃 실패, ${e.message}")
            }
        }
    }

    fun logout() {
        if (ApplicationClass.sharedPreferences.getString("type") == "parent") {
            parentLogout(ApplicationClass.sharedPreferences.getLong("id"))
        } else {
            childLogout(ApplicationClass.sharedPreferences.getLong("id"))
        }

        ApplicationClass.sharedPreferences.removeUser()
    }
}
