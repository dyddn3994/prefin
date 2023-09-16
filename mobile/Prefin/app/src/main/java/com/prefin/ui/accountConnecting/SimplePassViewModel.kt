package com.prefin.ui.accountConnecting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "Prefin_SimplePassViewModel"
class SimplePassViewModel : ViewModel() {
    private val _parentSimplePassRegisterSuccess = MutableLiveData<Boolean>()
    val parentSimplePassRegisterSuccess: LiveData<Boolean> get() = _parentSimplePassRegisterSuccess

    fun setParentSimplePass(parentId: Long, inputPass: String) {
        viewModelScope.launch {
            try {
                RetrofitUtil.signUpApi.parentSimplePassRegister(parentId, Parent(simplePass = inputPass))
                _parentSimplePassRegisterSuccess.value = true
            } catch (e: Exception) {
                Log.d(TAG, "setParentSimplePass: 부모 간편 비밀번호 등록 실패, ${e.message}")
                _parentSimplePassRegisterSuccess.value = false
            }
        }
    }

    private val _childSimplePassRegisterSuccess = MutableLiveData<Boolean>()
    val childSimplePassRegisterSuccess: LiveData<Boolean> get() = _childSimplePassRegisterSuccess

    fun setChildSimplePass(childId: Long, inputPass: String) {
        viewModelScope.launch {
            try {
                _childSimplePassRegisterSuccess.value = true
                RetrofitUtil.signUpApi.childSimplePassRegister(childId, Child(simplePass = inputPass))
            } catch (e: Exception) {
                Log.d(TAG, "setChildSimplePass: 자녀 간편 비밀번호 등록 실패, ${e.message}")
                _childSimplePassRegisterSuccess.value = false
            }
        }
    }
}
