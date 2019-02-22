package com.androidcourse.marvellisimo.dto

import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import com.androidcourse.marvellisimo.models.character.Character
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler
import com.androidcourse.marvellisimo.services.comics.ComicsServiceHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataHandler {
    var characters: List<Character>? = null
    var comics: List<Comics>? = null
    var character: Character? = null

    fun initializeData() {
        CharacterServiceHandler.getAllCharacters()
        ComicsServiceHandler.getAllComics()
    }

    fun getCharacter(characterId: Any) : Character {
        var c : Character? = null
        CharacterServiceHandler.getCharacterById(characterId.toString()).enqueue(object :
            Callback<CharacterDataWrapper> {
            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
//                character = response.body()!!.data.results[0]
//                setCharacterViewFields(character!!)
                c = response.body()!!.data.results[0]

            }

            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                t.message
            }
        })
        return c!!
    }
}