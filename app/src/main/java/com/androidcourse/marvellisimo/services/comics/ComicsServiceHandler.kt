package com.androidcourse.marvellisimo.services.comics

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.androidcourse.marvellisimo.adapters.comics.ComicsListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.dto.comics.ComicsDataWrapper
import com.androidcourse.marvellisimo.models.comics.Comics
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ComicsServiceHandler {
    const val API_KEY = "b81ab5acd75812bc101c025548dffbdb"
    const val HASH = "31e691f1a0196f2e7d372b067f1ce131"

    // TODO: Remove before delivery...
    //

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
            API_KEY, "1",
            HASH
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
        return SERVICE.getComicById(id.toInt(), API_KEY, "1", HASH)
    }
}