package com.prefin.util

import com.prefin.config.ApplicationClass
import com.prefin.model.api.ChildHomeApi
import com.prefin.model.api.LoanApi
import com.prefin.model.api.LoginApi
import com.prefin.model.api.ParentHomeApi
import com.prefin.model.api.PinMoneyApi
import com.prefin.model.api.QuestApi
import com.prefin.model.api.QuizApi
import com.prefin.model.api.SavingApi
import com.prefin.model.api.SignUpApi

class RetrofitUtil {
    companion object {
        val loginApi: LoginApi = ApplicationClass.retrofit.create(LoginApi::class.java)
        val loanApi: LoanApi = ApplicationClass.retrofit.create(LoanApi::class.java)
        val pinMoneyApi: PinMoneyApi = ApplicationClass.retrofit.create(PinMoneyApi::class.java)
        val questApi: QuestApi = ApplicationClass.retrofit.create(QuestApi::class.java)
        val savingApi: SavingApi = ApplicationClass.retrofit.create(SavingApi::class.java)
        val signUpApi: SignUpApi = ApplicationClass.retrofit.create(SignUpApi::class.java)
        val parentHomeApi: ParentHomeApi = ApplicationClass.retrofit.create(ParentHomeApi::class.java)
        val quizApi: QuizApi = ApplicationClass.retrofit.create(QuizApi::class.java)
        val childHomeApi : ChildHomeApi = ApplicationClass.retrofit.create(ChildHomeApi::class.java)
    }
}
