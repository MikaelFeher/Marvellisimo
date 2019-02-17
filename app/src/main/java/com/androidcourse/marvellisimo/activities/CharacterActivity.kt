package com.androidcourse.marvellisimo.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler

class CharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        if (intent.hasExtra("characterId")){
            val characterId = intent.extras.get("characterId")
            println("Id from intent: $characterId")
        }

//        CharacterServiceHandler.getCharacterById(id)
    }
}
