package com.prefin.ui.saving

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.SavingHistory
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "Prefin_SavingHomeViewModel"
class SavingHomeViewModel : ViewModel() {
    // 저축 내역
    private var _savingHistories = MutableLiveData<List<SavingHistory>>()
    val savingHistories: LiveData<List<SavingHistory>>
        get() = _savingHistories

    fun setSavingHistory(
        childId: Long,
    ) {
        viewModelScope.launch {
            try {
                _savingHistories.value = RetrofitUtil.savingApi.getSavingHistory(childId)
            } catch (e: Exception) {
                Log.d(TAG, "setSavingHistory: 저축 내역 조회 실패 ${e.message}")
                _savingHistories.value = listOf()
            }
        }
    }
}
