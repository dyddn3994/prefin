package com.prefin.ui.loan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.LoanHistory
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "Prefin_LoanHomeViewModel"
class LoanHomeViewModel : ViewModel() {
    // 대출 내역 조회
    private var _loanHistories = MutableLiveData<List<LoanHistory>>()
    val loanHistories: LiveData<List<LoanHistory>>
        get() = _loanHistories

    fun setLoanHistory(childId: Long) {
        viewModelScope.launch {
            try {
                _loanHistories.value = RetrofitUtil.loanApi.getLoanHistory(childId)
            } catch (e: Exception) {
                Log.d(TAG, "setLoanHistory: 대출 내역 조회 실패, ${e.message}")
                _loanHistories.value = listOf()
            }
        }
    }
}
