package com.prefin.ui.childHome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prefin.config.ApplicationClass
import com.prefin.model.dto.Child
import com.prefin.model.dto.Quiz
import com.prefin.util.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "Prefin_ChildHomeFragmentViewMo"
class ChildHomeFragmentViewModel : ViewModel() {
    private val _child = MutableLiveData<Child>()
    val child: LiveData<Child> get() = _child

    var quiz: Quiz? = null

    fun getChild() {
        viewModelScope.launch {
            try {
                val id = ApplicationClass.sharedPreferences.getLong("id")

                val response = RetrofitUtil.childHomeApi.getChildData(id)
                if (response != null) {
                    _child.value = response
                }
            } catch (e: Exception) {
                Log.d(TAG, "getChild: 자녀 조회 오류, ${e.message}")
            }
        }
    }

    fun getQuiz() {
        viewModelScope.launch {
            try {
                var id = ApplicationClass.sharedPreferences.getLong("id")
                val response = RetrofitUtil.quizApi.getQuizData(id)
                if (response != null) {
                    quiz = response
                }
            } catch (e: Exception) {
                Log.d(TAG, "getQuiz: 퀴즈 조회 오류, ${e.message}")
            }
        }
    }
}
