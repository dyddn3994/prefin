package com.prefin.util

import android.content.Context
import android.content.SharedPreferences
import com.prefin.config.ApplicationClass
import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent

class SharedPreferencesUtil(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(ApplicationClass.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    
    fun getString(key: String): String {
        return preferences.getString(key, null) ?: ""
    }
    fun getLong(key : String) : Long {
        return preferences.getLong(key, -1)
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

    fun addParentUser(parent : Parent) {
        val editor = preferences.edit()
        editor.putLong("id", parent.id)
        editor.putString("userName", parent.name)
        editor.putString("userId", parent.userId)
        editor.putString("userFCMToken", parent.fcmToken)
        editor.putString("type", "parent")

    }
    fun addChildUser(child : Child) {
        val editor = preferences.edit()
        editor.putLong("id", child.id)
        editor.putString("userName", child.name)
        editor.putString("userId", child.userId)
        editor.putString("userFCMToken", child.fcmToken)
        editor.putString("type", "child")

    }
}