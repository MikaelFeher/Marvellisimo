package com.androidcourse.marvellisimo.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler
import kotlinx.android.synthetic.main.activity_character_list.*

class CharacterListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        CharacterServiceHandler.getAllCharacters(character_list)
    }
}
