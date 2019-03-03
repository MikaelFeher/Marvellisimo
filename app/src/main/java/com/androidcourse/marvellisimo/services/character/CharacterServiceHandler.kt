package com.androidcourse.marvellisimo.services.character

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.androidcourse.marvellisimo.MainActivity
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import com.androidcourse.marvellisimo.models.character.Character
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object CharacterServiceHandler {

    const val API_KEY = "b81ab5acd75812bc101c025548dffbdb"
    const val HASH = "31e691f1a0196f2e7d372b067f1ce131"

    // TODO: Remove before delivery...
    // http://gateway.marvel.com/v1/public/characters?ts=1&apikey=b81ab5acd75812bc101c025548dffbdb&hash=31e691f1a0196f2e7d372b067f1ce131

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
            apikey = API_KEY,
            ts ="1",
            hash = HASH
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
            apikey = API_KEY,
            ts ="1",
            hash = HASH
        )
    }

    // Find characters by name...
    fun findCharacterByNameStartsWith(name: String) : Call<CharacterDataWrapper>{
        return SERVICE.findCharacterByNameStartsWith(
            nameStartsWith = name,
            apikey = API_KEY,
            ts ="1",
            hash = HASH
        )
    }

    fun findCharacterByComic(comicsId: String) : Call<CharacterDataWrapper> {
        return SERVICE.findCharacterByComic(
            comicsId = comicsId,
            apikey = API_KEY,
            ts ="1",
            hash = HASH
        )
    }

    fun getMoreCharacters(offset: Int) : Call<CharacterDataWrapper>{
        return SERVICE.getMoreCharacters(
            offset = offset,
            apikey = API_KEY,
            ts = "1",
            hash = HASH
        )
    }
}