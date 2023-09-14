package com.prefin.ui.quest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.QuestRegisterRequest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "QuestRegistViewModel"
class QuestRegistViewModel : ViewModel() {
    private var _isQuestRegistSuccess = MutableLiveData<Boolean>()
    val isQuestRegistSuccess: LiveData<Boolean>
        get() = _isQuestRegistSuccess

    // 퀘스트 등록
    fun registerQuest(
        childId: Long,
        endDate: Long,
        questId: Long,
        startDate: Long,
    ) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "registerQuest: $startDate")
                RetrofitUtil.questApi.questRegister(QuestRegisterRequest(childId, endDate, questId, startDate))
                _isQuestRegistSuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "registerQuest: 퀘스트 등록 실패\n")
                _isQuestRegistSuccess.value = false
            }
        }
    }
}
