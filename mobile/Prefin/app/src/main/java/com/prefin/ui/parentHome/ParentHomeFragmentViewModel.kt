package com.prefin.ui.parentHome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.config.ApplicationClass
import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ParentHomeFragmentViewM prefin"
class ParentHomeFragmentViewModel : ViewModel() {
    private val _parent = MutableLiveData<Parent>()
    val parent : LiveData<Parent> get() = _parent

    private val _childs = MutableLiveData<MutableList<Child>>()
    val childs : LiveData<MutableList<Child>> get() = _childs


    fun getParentData() {
        val id = ApplicationClass.sharedPreferences.getLong("id")
        try{
            viewModelScope.launch {
                val response = RetrofitUtil.parentHomeApi.getParentData(id)
                if(response != null){
                    _parent.value  = response
                }
            }
        }catch (e : Exception){
            Log.d(TAG, "getParentData: ${e.message}")
        }
       
    }
    fun getChildData(){
        val id = ApplicationClass.sharedPreferences.getLong("id")
        try {
            viewModelScope.launch {
                val response = RetrofitUtil.parentHomeApi.getChildData(id)
                if(response != null){
                    _childs.value = response as MutableList<Child>
                }
            }
        } catch (e : Exception){
            Log.d(TAG, "getChildData: ${e.message}")
            _childs.value = mutableListOf()
        }
     
    }

}