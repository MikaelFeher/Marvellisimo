package com.androidcourse.marvellisimo.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import com.androidcourse.marvellisimo.models.Character
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_character.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var character: Character
//        val characterName: TextView = findViewById(R.id.tv_character_name)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        var characterId : Any = 0

        if (intent.hasExtra("characterId")){
            characterId = intent.extras.get("characterId")
        }

//        val character = CharacterServiceHandler.getCharacterById(characterId.toString())
//        println("character: $character")

        CharacterServiceHandler.getCharacterById(characterId.toString()).enqueue(object :
            Callback<CharacterDataWrapper> {
            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                println(response.body()!!.data.results[0])
                character = response.body()!!.data.results[0]
                tv_character_name.text = character.name
            }
            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }
}
