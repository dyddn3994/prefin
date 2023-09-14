package com.prefin.model.api

import com.prefin.model.dto.Quiz
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizApi {

    @GET("quiz/today/{childId}")
    suspend fun getQuizData(@Path("childId") id : Long) : Quiz
    @POST("quiz/isCorrect/{childId}")
    suspend fun postAnswer(@Path("childId") id : Long, @Body quiz : Quiz) : Response<Boolean>
}