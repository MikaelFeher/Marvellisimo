package com.androidcourse.marvellisimo.activities.character

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler
import kotlinx.android.synthetic.main.activity_character_list.*

class CharacterListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        character_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        CharacterServiceHandler.getAllCharacters(character_list, this)

    }
}
