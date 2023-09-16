package com.prefin

import androidx.lifecycle.ViewModel
import com.prefin.model.dto.Child
import com.prefin.model.dto.LoanHistory
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

    var selectedLoanHistory: LoanHistory = LoanHistory()

    var fromFragment: String? = ""
    var tempSimplePass = ""

    // SimplePass 이동에 따른 임시 데이터 저장
    var savingFragmentSavingAmount = 0
    var withdrawFragmentWithdrawAmount = 0
    var pinMoneySendFragmentAmount = 0
    var pinMoneyRegistFragmentDate = 0L
    var pinMoneyRegistFragmentAmount = 0
    var parentFragmentSimplePassSuccess = false
    var loanResponseFragmentSuccess = false

    fun clearSimplePassMoving() {
        fromFragment = ""

        savingFragmentSavingAmount = 0
        withdrawFragmentWithdrawAmount = 0
        pinMoneySendFragmentAmount = 0
        pinMoneyRegistFragmentDate = 0L
        pinMoneyRegistFragmentAmount = 0
        parentFragmentSimplePassSuccess = false
        loanResponseFragmentSuccess = false
    }
}
