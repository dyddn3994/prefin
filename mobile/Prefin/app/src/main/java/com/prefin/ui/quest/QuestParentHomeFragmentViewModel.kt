package com.prefin.ui.quest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Quest
import com.prefin.model.dto.QuestOwned
import com.prefin.model.dto.QuestOwnedQuest
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "QuestParentHomeFragment prefin"
class QuestParentHomeFragmentViewModel : ViewModel() {

    private val _questList = MutableLiveData<MutableList<QuestOwnedQuest>>()
    val questList : LiveData<MutableList<QuestOwnedQuest>> get() = _questList


    fun getQuestList(id : Long) {
        try {
            viewModelScope.launch {
                val response = RetrofitUtil.questApi.childQuestList(id)
                if(response != null){
                    Log.d(TAG, "getQuestList: $response")
                    _questList.value = response as MutableList<QuestOwnedQuest>
                }
                else{
                    _questList.value = mutableListOf()
                }
            }
        } catch (e : Exception){
            Log.d(TAG, "통신 오류 : ${e.message}")
        }


    }


}