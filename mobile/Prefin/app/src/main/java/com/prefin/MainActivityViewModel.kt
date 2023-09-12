package com.prefin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

     var parentUser : Parent? = null

     var childUser : Child? = null

     var fcmToken : String? = null
}
