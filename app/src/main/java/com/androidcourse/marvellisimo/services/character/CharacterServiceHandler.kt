package com.androidcourse.marvellisimo.services.character

import android.annotation.SuppressLint
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import com.androidcourse.marvellisimo.helpers.Enums
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object CharacterServiceHandler {

    private val apikey = Enums.API_KEY.string
    private val hash = Enums.HASH.string

    private val okHttpClient = OkHttpClient.Builder()
        .build()

    private val SERVICE: CharacterService = Retrofit.Builder()
        .baseUrl("http://gateway.marvel.com/v1/public/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
        .create(CharacterService::class.java)

    // Get all characters...
    @SuppressLint("CheckResult")
    fun getAllCharacters() {
        SERVICE.getAllCharacters(
            apikey = apikey,
            ts ="1",
            hash = hash
        ).enqueue(object : Callback<CharacterDataWrapper> {
            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                DataHandler.characters!!.postValue(response.body()!!.data.results)
            }
            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }

    // Get single character...
    fun getCharacterById(id: String) : Call<CharacterDataWrapper>{

        return SERVICE.getCharacterById(
            id = id.toInt(),
            apikey = apikey,
            ts ="1",
            hash = hash
        )
    }

    // Find characters by name...
    fun findCharacterByNameStartsWith(name: String) : Call<CharacterDataWrapper>{
        return SERVICE.findCharacterByNameStartsWith(
            nameStartsWith = name,
            apikey = apikey,
            ts ="1",
            hash = hash
        )
    }

    // Find characters by comics they are associated with...
    fun findCharacterByComic(comicsId: String) : Call<CharacterDataWrapper> {
        return SERVICE.findCharacterByComic(
            comicsId = comicsId,
            apikey = apikey,
            ts ="1",
            hash = hash
        )
    }

    // Get characters dependant on the offset, needed for infinite scroll...
    fun getMoreCharacters(offset: Int) : Call<CharacterDataWrapper>{
        return SERVICE.getMoreCharacters(
            offset = offset,
            apikey = apikey,
            ts = "1",
            hash = hash
        )
    }
}