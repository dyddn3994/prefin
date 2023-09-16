package com.prefin.ui.loan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.LoanBorrowRequest
import com.prefin.model.dto.LoanGiveMoneyRequest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "LoanApplicationViewMode"
class LoanApplicationViewModel : ViewModel() {
    private val _loanApplySuccess = MutableLiveData<Boolean>()
    val loanApplySuccess: LiveData<Boolean> get() = _loanApplySuccess

    fun askForMoney(loanAmount: Int, parentId: Long, childId: Long) {
        viewModelScope.launch {
            try {
                RetrofitUtil.loanApi.askForMoney(LoanBorrowRequest(loanAmount, parentId, childId))
                _loanApplySuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "borrow: 대출 신청 실패\n대출금액: $loanAmount, 부모id: $parentId, 자녀id: $childId")
                _loanApplySuccess.value = false
            }
        }
    }
}
