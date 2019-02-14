package com.androidcourse.marvellisimo.services.character

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.androidcourse.marvellisimo.adapters.CharacterAdapter
import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import com.androidcourse.marvellisimo.models.Character
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


    //http://gateway.marvel.com/v1/public/characters?ts=1&apikey=b81ab5acd75812bc101c025548dffbdb&hash=31e691f1a0196f2e7d372b067f1ce131

    val okHttpClient = OkHttpClient.Builder()
        .build()

    val SERVICE: CharacterService = Retrofit.Builder()
        .baseUrl("http://gateway.marvel.com/v1/public/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
        .create(CharacterService::class.java)

    @SuppressLint("CheckResult")
    fun getAllCharacters(character_list : RecyclerView, context: Context) {

        SERVICE.getAllCharacters(
            API_KEY, "1",
            HASH
        ).enqueue(object : Callback<CharacterDataWrapper> {
            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                showData(
                    response.body()!!.data.results,
                    character_list,
                    context
                )
            }
            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }

    private fun showData(results: List<Character>, character_list: RecyclerView, context: Context) {
        character_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CharacterAdapter(results, context)
        }
    }
}