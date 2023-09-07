package com.prefin.util

import android.content.Context
import android.content.SharedPreferences
import com.prefin.config.ApplicationClass

class SharedPreferencesUtil(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    
    fun getString(key: String): String {
        return preferences.getString(key, null) ?: ""
    }
    
//    fun addUser(userLoginInfoDto: UserLoginInfoDto) {
//        preferences.edit().apply {
//            putLong("userId", userLoginInfoDto.userId)
//            putString("userEmail", userLoginInfoDto.userEmail)
//            putString("userNickname", userLoginInfoDto.userNickname)
//            putString("userProfileImg", userLoginInfoDto.userProfileImg)
//            putString(REFRESH_TOKEN, userLoginInfoDto.userToken)
//            apply()
//        }
//    }
    
    fun removeUser() {
        preferences.edit().clear().apply()
    }
}