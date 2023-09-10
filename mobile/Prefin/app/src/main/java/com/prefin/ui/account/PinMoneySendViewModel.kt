package com.prefin.ui.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.PinMoneyTransferRequest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "PinMoneySendViewModel"
class PinMoneySendViewModel : ViewModel() {
    private var _isPinMoneySendSuccess = MutableLiveData<Boolean>()
    val isPinMoneySendSuccess: LiveData<Boolean>
        get() = _isPinMoneySendSuccess

    // 용돈 전송
    fun pinMoneySend(
        parentId: Long,
        childId: Long,
        allowanceAmount: Int,
    ) {
        viewModelScope.launch {
            try {
                RetrofitUtil.pinMoneyApi.pinMoneyTransfer(PinMoneyTransferRequest(parentId, childId, allowanceAmount))
                _isPinMoneySendSuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "pinMoneySend: 용돈 전송 실패\n부모id: $parentId / 자녀id: $childId / 금액: $allowanceAmount")
                _isPinMoneySendSuccess.value = false
            }
        }
    }
}
