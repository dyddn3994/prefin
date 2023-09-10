package com.prefin.ui.accountConnecting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Child
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ChildAddFragmentViewMod_prefin"
class ChildAddFragmentViewModel : ViewModel() {

    private val _childList = MutableLiveData<MutableList<Child>> ()
    val childList : LiveData<MutableList<Child>> get() = _childList

    fun getChildList(id : Long) {
        viewModelScope.launch {
            try {
                val response =  RetrofitUtil.parentHomeApi.getChildData(id)

                _childList.value = response as MutableList<Child>

            }
            catch (e : Exception){
                Log.d(TAG, "getChildList: 통신 오류")
            }
        }
    }


}