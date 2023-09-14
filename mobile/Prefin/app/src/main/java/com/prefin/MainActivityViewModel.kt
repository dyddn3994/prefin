package com.prefin

import androidx.lifecycle.ViewModel
import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import com.prefin.model.dto.Quest
import com.prefin.model.dto.Quiz

class MainActivityViewModel : ViewModel() {
    var selectedChild: Child = Child()
    var parentUser: Parent? = null

    var childUser: Child? = null

    var fcmToken: String? = null

    var destinationFragment = 0

    var quiz: Quiz? = null

    var selectedQuest: Quest? = null
}
