package com.prefin.ui.quest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.QuestOwned
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "QuestChildHomeViewModel"
class QuestChildHomeViewModel : ViewModel() {
    // 퀘스트 완료 요청
    private var _questFinishRequestSuccess = MutableLiveData<Boolean>()
    val questFinishRequestSuccess: LiveData<Boolean>
        get() = _questFinishRequestSuccess

    fun questFinishRequest(questOwnedId: Long) {
        viewModelScope.launch {
            try {
                RetrofitUtil.questApi.questFinishRequest(questOwnedId)
                _questFinishRequestSuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "questFinishRequest: 퀘스트 완료 요청 실패\n퀘스트 소유 id: $questOwnedId")
                _questFinishRequestSuccess.value = false
            }
        }
    }

    // 자녀 퀘스트 리스트 조회
    private var _questList = MutableLiveData<List<QuestOwned>>()
    val questList: LiveData<List<QuestOwned>>
        get() = _questList

    fun getQuestList(childId: Long) {
        viewModelScope.launch {
            try {
                _questList.value = RetrofitUtil.questApi.childQuestList(childId)
            } catch (e: Exception) {
                Log.d(TAG, "getQuestList: 퀘스트 리스트 조회 실패\n자녀id: $childId")
                _questList.value = listOf()
            }
        }
    }
}
