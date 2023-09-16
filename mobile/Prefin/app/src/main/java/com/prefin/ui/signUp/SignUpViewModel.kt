package com.prefin.ui.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Parent
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "SignUpViewModel prefin"
class SignUpViewModel : ViewModel() {
    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess : LiveData<Boolean> get() = _signUpSuccess
    var parentUser : Parent = Parent()
    var userId = 0L;
    fun signUp() {
        viewModelScope.launch {
            try{
                val response = RetrofitUtil.signUpApi.parentSignUp(parentUser)
                if(response.isSuccessful){
                    val id = response.body()
                    if(id != null && id != -1L){
                        userId = id
                        Log.d(TAG, "signUp: 회원가입 성공")
                        _signUpSuccess.value = true
                    }
                    else if(id == -1L
                    ){
                        userId = id
                        _signUpSuccess.value = false
                    }
                    else{
                        Log.d(TAG, "signUp: 회원가입 실패")
                    }

                }
            }
            catch (e : Exception){
                _signUpSuccess.value = false
                Log.d(TAG, "signUp: 회원가입 실패")
            }

        }


    }
}