package com.prefin.ui.saving

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.config.ApplicationClass
import com.prefin.model.dto.Child
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ChildAccountChooseFragm prefin"
class ChildAccountChooseFragmentViewModel : ViewModel() {

    private val _childList = MutableLiveData<MutableList<Child>>()
    val childList : LiveData<MutableList<Child>> get() = _childList


    fun getChildList() {
        val id = ApplicationClass.sharedPreferences.getLong("id")
        viewModelScope.launch {
            try{
                val response = RetrofitUtil.parentHomeApi.getChildData(id)
                if(response != null){
                    _childList.value = response as MutableList<Child>
                }
            }
            catch (e : Exception){
                Log.d(TAG, "getChildList: 아이들 정보 가져오기 실패")
            }

        }

    }


}