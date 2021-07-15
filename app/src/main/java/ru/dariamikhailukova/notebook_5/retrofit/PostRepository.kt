package ru.dariamikhailukova.notebook_5.retrofit

import retrofit2.Response


class PostRepository {

    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getPost()
    }
}