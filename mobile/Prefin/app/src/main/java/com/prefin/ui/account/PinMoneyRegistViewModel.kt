package com.prefin.ui.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.PinMoneySetRequest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "PinMoneyRegistViewModel"
class PinMoneyRegistViewModel : ViewModel() {
    private var _isPinMoneySetSuccess = MutableLiveData<Boolean>()
    val isPinMoneySetSuccess: LiveData<Boolean>
        get() = _isPinMoneySetSuccess

    // 정기 용돈 등록
    fun pinMoneySet(
        payday: Long,
        allowanceAmount: Int,
        parentId: Long,
        childId: Long,
    ) {
        viewModelScope.launch {
            try {
                RetrofitUtil.pinMoneyApi.pinMoneySet(PinMoneySetRequest(payday, allowanceAmount, parentId, childId))
                _isPinMoneySetSuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "pinMoneySet: 용돈 등록 실패 \n용돈 등록 날짜: $payday / 금액: $allowanceAmount / 부모id: $parentId / 자녀id: $childId")
                _isPinMoneySetSuccess.value = false
            }
        }
    }
}
