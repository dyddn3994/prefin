package com.prefin.ui.saving

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.SavingRequest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "WithdrawViewModel"
class WithdrawViewModel : ViewModel() {
    // 인출
    private var _isWithdrawSuccess = MutableLiveData<Boolean>()
    val isWithdrawSuccess: LiveData<Boolean>
        get() = _isWithdrawSuccess

    // 인출
    fun withdraw(
        childId: Long,
        balance: Int,
    ) {
        viewModelScope.launch {
            try {
                RetrofitUtil.savingApi.withdraw(childId, SavingRequest(balance))
                _isWithdrawSuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "withdraw: 인출 실패\n자녀id: $childId / 금액: $balance")
                _isWithdrawSuccess.value = false
            }
        }
    }
}
