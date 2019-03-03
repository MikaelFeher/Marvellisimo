package com.androidcourse.marvellisimo.services.comics

import android.annotation.SuppressLint
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.dto.comics.ComicsDataWrapper
import com.androidcourse.marvellisimo.helpers.Enums
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ComicsServiceHandler {
    private val apikey = Enums.API_KEY.string
    private val hash = Enums.HASH.string

    private val okHttpClient = OkHttpClient.Builder()
        .build()

    private val SERVICE: ComicsService = Retrofit.Builder()
        .baseUrl("http://gateway.marvel.com/v1/public/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ComicsService::class.java)

    // Get all comics...
    @SuppressLint("CheckResult")
    fun getAllComics() {
        SERVICE.getAllComics(
            apikey = apikey,
            ts = "1",
            hash = hash
        ).enqueue(object : Callback<ComicsDataWrapper> {
            override fun onResponse(call: Call<ComicsDataWrapper>, response: Response<ComicsDataWrapper>) {
                DataHandler.comics!!.postValue(response.body()!!.data.results)
            }

            override fun onFailure(call: Call<ComicsDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }

    // Get single comic...
    fun getComicById(id: String): Call<ComicsDataWrapper> {
        return SERVICE.getComicById(id = id.toInt(), apikey = apikey, ts = "1", hash = hash)
    }

    fun findComicByName(inputValue: String): Call<ComicsDataWrapper> {
        return SERVICE.findComicsByName(
            titleStartsWith = inputValue,
                    apikey = apikey,
                    ts = "1",
                    hash = hash
                )
    }

    fun findComicByCharacter(characterId: String) : Call<ComicsDataWrapper> {
        return SERVICE.findComicByCharacter(
            characterId = characterId,
            apikey = apikey,
            ts = "1",
            hash = hash
        )
    }

    fun getMoreComics(offset: Int): Call<ComicsDataWrapper> {
        return SERVICE.getMoreComics(
            offset = offset,
            apikey = apikey,
            ts = "1",
            hash = hash
        )
    }
}