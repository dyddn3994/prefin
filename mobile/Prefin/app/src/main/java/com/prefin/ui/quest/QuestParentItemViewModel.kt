package com.prefin.ui.quest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Quest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "QuestParentItemViewMode"
class QuestParentItemViewModel : ViewModel() {
    private var _questItemList = MutableLiveData<List<Quest>>()
    val questItemList: LiveData<List<Quest>>
        get() = _questItemList

    // 퀘스트 아이템 리스트 조회
    fun requestQuestItemList(
        id: Long,
    ) {
        viewModelScope.launch {
            try {
                _questItemList.value = RetrofitUtil.questApi.parentQuestItemList(id)
                Log.d(TAG, "requestQuestItemList: ${_questItemList.value}")
            } catch (e: Exception) {
                _questItemList.value = listOf()
                Log.d(TAG, "requestQuestItemList: 퀘스트 아이템 리스트 조회 실패\n자녀id: $id")
            }
        }
    }
}
