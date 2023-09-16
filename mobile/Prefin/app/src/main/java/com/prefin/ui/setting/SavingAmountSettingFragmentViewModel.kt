package com.prefin.ui.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Parent
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.lang.Exception

private const val TAG = "SavingAmountSettingFrag_prefin"
class SavingAmountSettingFragmentViewModel : ViewModel() {

    private val _settingSuccess = MutableLiveData<Boolean>()
    val settingSuccess : LiveData<Boolean> get() = _settingSuccess


    fun setSavingInterestAmount(parent : Parent){
        viewModelScope.launch {
            try{
                val response = RetrofitUtil.savingApi.maxSaving(parent.id, parent)
                if(response.isSuccessful){
                    _settingSuccess.value = response.body()
                }
                else{
                    _settingSuccess.value = false
                }
            }catch (e : Exception){
                Log.d(TAG, "setSavingInterestAmount 오류: ${e.message}")
                _settingSuccess.value = false
            }

        }
    }
}