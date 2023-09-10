package com.prefin.model.api

import com.prefin.model.dto.Child
import com.prefin.model.dto.Parent
import retrofit2.http.GET
import retrofit2.http.Path

interface ParentHomeApi {

    @GET("parent/child/{id}")
    suspend fun getChildData(@Path("id") id : Long) : List<Child>

    @GET("parent/account/{id}")
    suspend fun getParentData(@Path("id") id : Long) : Parent
}