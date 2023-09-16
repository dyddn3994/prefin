package com.prefin.ui.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.AccountHistory
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "Prefin_AccountHistoryViewModel"
class AccountHistoryViewModel : ViewModel() {
    private val _accountHistory = MutableLiveData<List<AccountHistory>>()
    val accountHistory: LiveData<List<AccountHistory>> get() = _accountHistory

    fun setAccountHistory(childId: Long) {
        try {
            viewModelScope.launch {
                _accountHistory.value = RetrofitUtil.pinMoneyApi.getAccountHistory(childId)
            }
        } catch (e: Exception) {
            Log.d(TAG, "계좌 내역 조회 오류 : ${e.message}")
            _accountHistory.value = listOf()
        }
    }
}
