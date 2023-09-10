package com.prefin.ui.quest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.QuestCreateRequest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "QuestCreateViewModel"
class QuestCreateViewModel : ViewModel() {
    private var _isQuestCreateSuccess = MutableLiveData<Boolean>()
    val isQuestCreateSuccess: LiveData<Boolean>
        get() = _isQuestCreateSuccess

    // 퀘스트 생성
    fun createQuest(
        parentId: Long,
        reward: Int,
        title: String,
    ) {
        viewModelScope.launch {
            try {
                RetrofitUtil.questApi.questCreate(QuestCreateRequest(parentId, reward, title))
                _isQuestCreateSuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "createQuest: 퀘스트 등록 실패")
                _isQuestCreateSuccess.value = false
            }
        }
    }
}
