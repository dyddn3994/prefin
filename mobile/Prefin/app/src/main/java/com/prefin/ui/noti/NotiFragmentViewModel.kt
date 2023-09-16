package com.prefin.ui.noti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.config.ApplicationClass
import com.prefin.model.local.NotiMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotiFragmentViewModel : ViewModel() {

    private val  _notiList = MutableLiveData<List<NotiMessage>>()

    val notiList : LiveData<List<NotiMessage>>
        get() = _notiList


    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = ApplicationClass.notiMessageDatabase.notiMessageDao.getAll()
            _notiList.postValue(data)
        }
    }




}