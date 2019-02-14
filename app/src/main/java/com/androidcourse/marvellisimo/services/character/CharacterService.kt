package com.androidcourse.marvellisimo.services.character

import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("characters")
    fun getAllCharacters(@Query("apikey")apikey:String, @Query("ts")ts:String, @Query("hash") hash:String): Call<CharacterDataWrapper>
}