package com.androidcourse.marvellisimo.retrofit

import com.androidcourse.marvellisimo.dto.CharacterDataWrapper
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

    const val API_KEY = "b81ab5acd75812bc101c025548dffbdb"
    const val HASH = "31e691f1a0196f2e7d372b067f1ce131"

    //http://gateway.marvel.com/v1/public/characters?ts=1&apikey=b81ab5acd75812bc101c025548dffbdb&hash=31e691f1a0196f2e7d372b067f1ce131

interface MarvelService {
    @GET("characters")
    fun getAllCharacters(): Single<CharacterDataWrapper>

    companion object {
        operator fun invoke(): MarvelService {
            val requestInterceptor = Interceptor {chain ->  
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("ts", "1")
                    .addQueryParameter("apikey", API_KEY)
                    .addQueryParameter("hash", HASH)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("http://gateway.marvel.com/v1/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(MarvelService::class.java)

        }
    }
}