package com.prefin.util

import com.prefin.config.ApplicationClass
import com.prefin.model.api.LoanApi
import com.prefin.model.api.LoginApi
import com.prefin.model.api.PinMoneyApi
import com.prefin.model.api.QuestApi
import com.prefin.model.api.SavingApi

class RetrofitUtil {
    companion object {
        val loginApi: LoginApi = ApplicationClass.retrofit.create(LoginApi::class.java)
        val loanApi: LoanApi = ApplicationClass.retrofit.create(LoanApi::class.java)
        val pinMoneyApi: PinMoneyApi = ApplicationClass.retrofit.create(PinMoneyApi::class.java)
        val questApi: QuestApi = ApplicationClass.retrofit.create(QuestApi::class.java)
        val savingApi: SavingApi = ApplicationClass.retrofit.create(SavingApi::class.java)
    }
}
