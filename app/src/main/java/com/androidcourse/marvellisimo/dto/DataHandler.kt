package com.androidcourse.marvellisimo.dto

import com.androidcourse.marvellisimo.models.character.Character
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler
import com.androidcourse.marvellisimo.services.comics.ComicsServiceHandler

object DataHandler {
    var characters: List<Character>? = null
    var comics: List<Comics>? = null

    fun initializeData() {
        CharacterServiceHandler.getAllCharacters()
        ComicsServiceHandler.getAllComics()
    }
}