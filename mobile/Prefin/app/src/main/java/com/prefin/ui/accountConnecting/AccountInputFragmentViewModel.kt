package com.prefin.ui.accountConnecting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Parent
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "AccountInputFragmentVie_prefin"
class AccountInputFragmentViewModel : ViewModel() {
    private val _accountRegisterSuccess = MutableLiveData<Boolean>()
    val accountRegisterSuccess : LiveData<Boolean> get() = _accountRegisterSuccess
    fun accountRegister(id : Long, user : Parent){
        viewModelScope.launch {
            try{
                val response = RetrofitUtil.signUpApi.accountRegister(id, user)
                if(response.isSuccessful){
                    _accountRegisterSuccess.value = response.body()!!
                    Log.d(TAG, "accountRegister: 계좌 등록 성공 ")

                }
            }catch (e : Exception){
                Log.d(TAG, "accountRegister: 계좌 등록 실패 ")
                Log.d(TAG, "계좌 등록 실패 원인: ${e.message}")
            }

        }

    }
}