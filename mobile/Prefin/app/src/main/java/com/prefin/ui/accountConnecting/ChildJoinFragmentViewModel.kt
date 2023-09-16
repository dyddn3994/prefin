package com.prefin.ui.accountConnecting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.model.dto.Child
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "ChildJoinFragmentViewMo_prefin"
class ChildJoinFragmentViewModel : ViewModel() {
    private val _childJoinSuccess = MutableLiveData<Boolean>()
    var childUser = Child()
    val childJoinSuccess : LiveData<Boolean> get() = _childJoinSuccess

    fun childJoin(child : Child){
        viewModelScope.launch {
            try{
                val response = RetrofitUtil.signUpApi.childSignUp(child)
                if(response != null && response != -1L){
                    childUser = child
                    childUser.id = response
                    _childJoinSuccess.value = true
                }
                else if(response == -1L){
                    childUser.id = response
                    _childJoinSuccess.value = false
                }
            }catch (e : Exception){
                Log.d(TAG, "childJoin: 통신 오류")
            }
        }
    }
}