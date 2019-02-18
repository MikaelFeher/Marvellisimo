package com.androidcourse.marvellisimo.services.comics

import com.androidcourse.marvellisimo.dto.comics.ComicsDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsService {
    @GET("comics")
    fun getAllComics(
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<ComicsDataWrapper>
}