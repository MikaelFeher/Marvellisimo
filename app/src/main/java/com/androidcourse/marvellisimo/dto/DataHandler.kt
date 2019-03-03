package com.androidcourse.marvellisimo.dto

import android.arch.lifecycle.MutableLiveData
import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import com.androidcourse.marvellisimo.dto.comics.ComicsDataWrapper
import com.androidcourse.marvellisimo.models.Realm.Favourite
import com.androidcourse.marvellisimo.models.character.Character
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler
import com.androidcourse.marvellisimo.services.comics.ComicsServiceHandler
import io.realm.RealmResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataHandler {
    var characters: MutableLiveData<List<Character>> = MutableLiveData()
    var comics: MutableLiveData<List<Comics>>? = MutableLiveData()
    var character: MutableLiveData<Character> = MutableLiveData()
    var comic: MutableLiveData<Comics> = MutableLiveData()
    var characterSearchResult: MutableLiveData<List<Character>>? = MutableLiveData()
    var comicSearchResult: MutableLiveData<List<Comics>>? = MutableLiveData()
    var charactersByComic: MutableLiveData<List<Character>>? = MutableLiveData()
    var comicsByCharacter: MutableLiveData<List<Comics>>? = MutableLiveData()
    lateinit var favouritesList: RealmResults<Favourite>
    var characterTotal = 40
    var characterOffset = 20

    fun initializeData() {
        CharacterServiceHandler.getAllCharacters()
        ComicsServiceHandler.getAllComics()
    }

    fun getMoreCharacters() {

        CharacterServiceHandler.getMoreCharacters(offset = characterOffset)
            .enqueue(object : Callback<CharacterDataWrapper> {
                override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                    characterTotal = if (characterTotal == 40) response.body()!!.data.total else characterTotal

                    val result = response.body()!!.data.results
                    println("result: ${result[0].name}")
                    var newList = ArrayList<Character>()
                    newList.addAll(result)
                    newList.addAll(characters.value!!)
                    var newLIstSorted = newList.sortedBy { it.name }
                    var filteredLIst =
                        newLIstSorted.filterIndexed { index, it -> if (index + 1 <= newLIstSorted.size - 1) it.name != newLIstSorted[index + 1].name else false }

                    characters.postValue(filteredLIst)

                    println("characters: ${characters.value!!.size}")
                }

                override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                    t.message
                }
            })
        characterOffset += 20
    }

    fun getCharacterById(characterId: String) {
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

    fun findCharacterByName(name: String) {
        CharacterServiceHandler.findCharacterByNameStartsWith(name).enqueue(object : Callback<CharacterDataWrapper> {

            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                if (response.body()!!.data.count == 0) DataHandler.characterSearchResult!!.postValue(null)
                else characterSearchResult!!.postValue(response.body()!!.data.results)
            }

            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }

    fun findCharacterByComic(comicId: String) {
        CharacterServiceHandler.findCharacterByComic(comicId).enqueue(object : Callback<CharacterDataWrapper> {
            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                charactersByComic!!.postValue(response.body()!!.data.results)
            }

            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }

    fun getComicById(comicId: Any) {
        ComicsServiceHandler.getComicById(comicId.toString()).enqueue(object : Callback<ComicsDataWrapper> {
            override fun onResponse(call: Call<ComicsDataWrapper>, response: Response<ComicsDataWrapper>) {
                comic.postValue(response.body()!!.data.results[0])
            }

            override fun onFailure(call: Call<ComicsDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }

    fun findComicByName(title: String) {
        ComicsServiceHandler.findComicByName(title).enqueue(object : Callback<ComicsDataWrapper> {

            override fun onResponse(call: Call<ComicsDataWrapper>, response: Response<ComicsDataWrapper>) {
                if (response.body()!!.data.count == 0) DataHandler.comicSearchResult!!.postValue(null)
                else DataHandler.comicSearchResult!!.postValue(response.body()!!.data.results)
            }

            override fun onFailure(call: Call<ComicsDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }

    fun findComicsByCharacter(characterId: String) {
        ComicsServiceHandler.findComicByCharacter(characterId).enqueue(object : Callback<ComicsDataWrapper> {
            override fun onResponse(call: Call<ComicsDataWrapper>, response: Response<ComicsDataWrapper>) {
                comicsByCharacter!!.postValue(response.body()!!.data.results)
            }

            override fun onFailure(call: Call<ComicsDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }
}