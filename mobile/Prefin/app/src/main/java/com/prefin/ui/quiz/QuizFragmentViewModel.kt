package com.prefin.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.config.ApplicationClass
import com.prefin.model.dto.Quiz
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

class QuizFragmentViewModel : ViewModel() {

    private val _quiz = MutableLiveData<Quiz>()
    val quiz : LiveData<Quiz> get() = _quiz


    private val _isCorrected  = MutableLiveData<Boolean>()
    val isCorrected : LiveData<Boolean> get() = _isCorrected

    fun getQuiz(){
        viewModelScope.launch {
            var id = ApplicationClass.sharedPreferences.getLong("id")
            val response = RetrofitUtil.quizApi.getQuizData(id)
            if(response != null) {
                _quiz.value  = response
            }
        }

    }

    fun postAnswer(quiz : Quiz){
        viewModelScope.launch {
            val id = ApplicationClass.sharedPreferences.getLong("id")
            val response = RetrofitUtil.quizApi.postAnswer(id, quiz)
            if(response.isSuccessful){
                _isCorrected.value = response.body()
            }
        }
    }
}