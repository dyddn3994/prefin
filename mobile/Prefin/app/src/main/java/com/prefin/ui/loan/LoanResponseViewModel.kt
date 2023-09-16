package com.prefin.ui.loan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.LoanGiveMoneyRequest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "Prefin_LoanResponseViewModel"
class LoanResponseViewModel : ViewModel() {
    private val _loanGiveMoneySuccess = MutableLiveData<Boolean>()
    val loanGiveMoneySuccess: LiveData<Boolean> get() = _loanGiveMoneySuccess

    fun giveMoney(loanId: Long) {
        viewModelScope.launch {
            try {
                RetrofitUtil.loanApi.giveMoney(LoanGiveMoneyRequest(loanId))
                _loanGiveMoneySuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "giveMoney: 대출금 전송 실패, ${e.message}")
                _loanGiveMoneySuccess.value = false
            }
        }
    }
}
