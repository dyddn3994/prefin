package com.prefin.util

import com.prefin.config.ApplicationClass
import com.prefin.model.api.LoginApi
import com.prefin.model.api.ParentHomeApi
import com.prefin.model.api.SignUpApi

class RetrofitUtil {
    companion object {
        val loginApi: LoginApi = ApplicationClass.retrofit.create(LoginApi::class.java)
        val signUpApi: SignUpApi = ApplicationClass.retrofit.create(SignUpApi::class.java)
        val parentHomeApi : ParentHomeApi = ApplicationClass.retrofit.create(ParentHomeApi::class.java)
    }
}
