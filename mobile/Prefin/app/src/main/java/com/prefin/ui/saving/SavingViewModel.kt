package com.prefin.ui.saving

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.SavingRequest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "SavingViewModel"
class SavingViewModel : ViewModel() {
    // 저축
    private var _isSavingSuccess = MutableLiveData<Boolean>()
    val isSavingSuccess: LiveData<Boolean>
        get() = _isSavingSuccess

    // 저축
    fun saving(
        childId: Long,
        balance: Int,
    ) {
        viewModelScope.launch {
            try {
                RetrofitUtil.savingApi.saving(childId, SavingRequest(balance))
                _isSavingSuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "saving: 저축 실패\n자녀id: $childId / 금액: $balance")
                _isSavingSuccess.value = false
            }
        }
    }

}
