package com.prefin.util

import com.prefin.config.ApplicationClass
import com.prefin.model.api.LoginApi

class RetrofitUtil {
    companion object {
        val loginApi: LoginApi = ApplicationClass.retrofit.create(LoginApi::class.java)
    }
}
