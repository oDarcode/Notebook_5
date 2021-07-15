package ru.dariamikhailukova.notebook_5.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("posts/84")
    suspend fun getPost(): Response<Post>
}