package com.prefin.ui.setting

import androidx.lifecycle.ViewModel
import com.prefin.config.ApplicationClass

class SettingFragmentViewModel : ViewModel() {

    fun logout(){
        ApplicationClass.sharedPreferences.removeUser()
    }
}