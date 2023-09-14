package com.prefin.ui.quest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Quest
import com.prefin.model.dto.QuestOwned
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

class QuestParentHomeFragmentViewModel : ViewModel() {

    private val _questList = MutableLiveData<MutableList<QuestOwned>>()
    val questList : LiveData<MutableList<QuestOwned>> get() = _questList


    fun getQuestList(id : Long) {

        viewModelScope.launch {
            val response = RetrofitUtil.questApi.childQuestList(id)
            if(response != null){
                _questList.value = response as MutableList<QuestOwned>
            }
        }

    }


}