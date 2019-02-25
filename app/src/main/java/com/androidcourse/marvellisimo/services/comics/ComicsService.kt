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
        @Path("id") id: Int,
        @Query("limit") limit: Int = 100,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<ComicsDataWrapper>

    @GET("comics")
    fun findComicsByName(
        @Query("limit") limit: Int = 100,
        @Query("titleStartsWith") titleStartsWith: String,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<ComicsDataWrapper>

    @GET("comics")
    fun findComicByCharacter(
        @Query("limit") limit: Int = 100,
        @Query("characters") characterId: String,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<ComicsDataWrapper>
}

