package com.prefin

import androidx.lifecycle.ViewModel
import com.prefin.model.dto.Parent

class MainActivityViewModel : ViewModel() {
    var selectedChildId: Long = 0L
    var parentUser: Parent? = null
}
