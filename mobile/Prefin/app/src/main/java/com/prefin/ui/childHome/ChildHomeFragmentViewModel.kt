package com.prefin.ui.childHome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prefin.config.ApplicationClass
import com.prefin.model.dto.Child
import com.prefin.model.dto.Quiz
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

class ChildHomeFragmentViewModel : ViewModel() {
   private val _child = MutableLiveData<Child>()
    val child : LiveData<Child> get() = _child

    var quiz : Quiz? = null

    fun getChild() {
     viewModelScope.launch {
         val id = ApplicationClass.sharedPreferences.getLong("id")

         val response = RetrofitUtil.childHomeApi.getChildData(id)
         if(response != null){
             _child.value = response
         }
     }
    }

    fun getQuiz(){
        viewModelScope.launch {
            var id = ApplicationClass.sharedPreferences.getLong("id")
            val response = RetrofitUtil.quizApi.getQuizData(id)
            if(response != null) {
                quiz = response
            }
        }

    }
}