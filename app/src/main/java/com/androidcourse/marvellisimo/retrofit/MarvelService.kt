package com.androidcourse.marvellisimo.retrofit

import com.androidcourse.marvellisimo.dto.CharacterDataWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {
    @GET("characters")
    fun getAllCharacters(@Query("apikey")apikey:String, @Query("ts")ts:String, @Query("hash") hash:String): Single<CharacterDataWrapper>
}