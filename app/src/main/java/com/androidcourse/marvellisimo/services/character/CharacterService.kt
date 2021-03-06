package com.androidcourse.marvellisimo.services.character

import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET("characters")
    fun getAllCharacters(
        @Query("limit") limit: Int = 40,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<CharacterDataWrapper>

    @GET("characters/{id}")
    fun getCharacterById(
        @Path("id") id: Int,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<CharacterDataWrapper>

    @GET("characters")
    fun findCharacterByNameStartsWith(
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<CharacterDataWrapper>

    @GET("characters")
    fun findCharacterByComic(
        @Query("comics") comicsId: String,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<CharacterDataWrapper>

    @GET("characters")
    fun getMoreCharacters(
        @Query("limit") limit: Int = 40,
        @Query("offset") offset: Int,
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<CharacterDataWrapper>
}