package com.androidcourse.marvellisimo.dto

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import com.androidcourse.marvellisimo.dto.comics.ComicsDataWrapper
import com.androidcourse.marvellisimo.models.character.Character
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.models.comics.Image
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler
import com.androidcourse.marvellisimo.services.comics.ComicsServiceHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataHandler {
    var characters: List<Character>? = null
    var comics: List<Comics>? = null
    var character: MutableLiveData<Character> = MutableLiveData()
    var comic: MutableLiveData<Comics> = MutableLiveData()

    fun initializeData() {
        CharacterServiceHandler.getAllCharacters()
        ComicsServiceHandler.getAllComics()
    }

    fun getCharacter(characterId: String) {

        CharacterServiceHandler.getCharacterById(characterId).enqueue(object :
            Callback<CharacterDataWrapper> {
            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                character.postValue(response.body()!!.data.results[0])
            }

            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }

    fun getComic(comicId: Any) {
        ComicsServiceHandler.getComicById(comicId.toString()).enqueue(object : Callback<ComicsDataWrapper> {
            override fun onResponse(call: Call<ComicsDataWrapper>, response: Response<ComicsDataWrapper>) {
                comic.postValue(response.body()!!.data.results[0])
            }

            override fun onFailure(call: Call<ComicsDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }
}