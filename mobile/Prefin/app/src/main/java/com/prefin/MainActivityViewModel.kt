package com.prefin

import androidx.lifecycle.ViewModel

import com.prefin.model.dto.Child

import com.prefin.model.dto.Parent
import com.prefin.model.dto.Quiz

class MainActivityViewModel : ViewModel() {
    var selectedChildId: Long = 0L
    var parentUser: Parent? = null



    var childUser : Child? = null

    var fcmToken : String? = null

    var quiz : Quiz? = null

}
