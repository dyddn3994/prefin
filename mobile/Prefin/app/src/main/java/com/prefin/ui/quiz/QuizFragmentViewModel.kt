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

private const val TAG = "QuizFragmentViewModel"
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
            } catch (e: Exception) {
                Log.d(TAG, "getQuiz: 퀴즈 조회 실패\n${e.message}")
            }
        }
    }

    fun postAnswer(quiz: Quiz) {
        viewModelScope.launch {
            val id = ApplicationClass.sharedPreferences.getLong("id")
            val response = RetrofitUtil.quizApi.postAnswer(id, quiz)
            if (response.isSuccessful) {
                _isCorrected.value = response.body()
            }
        }
    }
}
