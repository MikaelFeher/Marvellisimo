package com.androidcourse.marvellisimo.services.comics

import com.androidcourse.marvellisimo.dto.comics.ComicsDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicsService {
    @GET("comics")
    fun getAllComics(
        @Query("limit") limit: Int = 100,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<ComicsDataWrapper>

    @GET("comics/{id}")
    fun getComicById(
        @Query("limit") limit: Int = 100,
        @Path("id") id: Int,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<ComicsDataWrapper>

    @GET("comics")
    fun findComicsByName(
        @Query("limit") limit: Int = 100,
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<ComicsDataWrapper>
}

