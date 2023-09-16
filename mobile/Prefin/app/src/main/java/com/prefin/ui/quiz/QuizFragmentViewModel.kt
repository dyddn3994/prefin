package com.prefin.ui.quiz

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.config.ApplicationClass
import com.prefin.model.dto.Quiz
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.log

private const val TAG = "QuizFragmentViewModel prefin"
class QuizFragmentViewModel : ViewModel() {

    private val _quiz = MutableLiveData<Quiz>()
    val quiz: LiveData<Quiz> get() = _quiz

    private val _isCorrected = MutableLiveData<Boolean>()
    val isCorrected: LiveData<Boolean> get() = _isCorrected

    fun getQuiz() {
        viewModelScope.launch {
            try {
                var id = ApplicationClass.sharedPreferences.getLong("id")
                val response = RetrofitUtil.quizApi.getQuizData(id)
                if (response != null) {
                    _quiz.value = response
                }
                else{
                    _quiz.value = Quiz()
                }
            } catch (e: Exception) {
                _quiz.value = Quiz()
                Log.d(TAG, "getQuiz: 퀴즈 조회 실패\n${e.message}")
            }

        }
    }

    fun postAnswer(quiz: Quiz) {
        viewModelScope.launch {
            try{
                val id = ApplicationClass.sharedPreferences.getLong("id")
                val response = RetrofitUtil.quizApi.postAnswer(id, quiz)
                if(response.isSuccessful){
                    _isCorrected.value = response.body()
                }
            } catch (e : Exception){
                Log.d(TAG, "postAnswer: ${e.message}")
            }
        }
    }
}
