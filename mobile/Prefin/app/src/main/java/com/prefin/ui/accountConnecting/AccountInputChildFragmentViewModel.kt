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

private const val TAG = "AccountInputChildFragme_prefin"
class AccountInputChildFragmentViewModel : ViewModel() {
    private val _accountChildSuccess = MutableLiveData<Boolean>()
    val accountChildSuccess : LiveData<Boolean> get() = _accountChildSuccess


    fun childAccountRegister(id : Long, child : Child){
        viewModelScope.launch {
            try{
                val response = RetrofitUtil.signUpApi.childAccountRegister(id, child)
                if(response.isSuccessful){
                    if(response.body()!!){
                        _accountChildSuccess.value = true
                        Log.d(TAG, "childAccountRegister: 자녀 계좌 등록 성공")
                    }
                }
            } catch (e : Exception){
                Log.d(TAG, "childAccountRegister: 전송 오류")
            }
        }
    }
}