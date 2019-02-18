package com.androidcourse.marvellisimo.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.character.CharacterDataWrapper
import com.androidcourse.marvellisimo.models.Character
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_character.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterActivity : AppCompatActivity() {

    private var character: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        var characterId : Any = 0

        if (intent.hasExtra("characterId")){
            characterId = intent.extras.get("characterId")
        }

        getCharacter(characterId)
    }

    private fun setCharacterViewFields(character: Character) {
        tv_character_name.text = character.name
        tv_character_description.text = if (character.description.isNotEmpty()) character.description else "No description available..."
        createImage(character, iv_character_image)
    }

    private fun getCharacter(characterId: Any) {

        CharacterServiceHandler.getCharacterById(characterId.toString()).enqueue(object :
            Callback<CharacterDataWrapper> {
            override fun onResponse(call: Call<CharacterDataWrapper>, response: Response<CharacterDataWrapper>) {
                println(response.body()!!.data.results[0])
                character = response.body()!!.data.results[0]
                setCharacterViewFields(character!!)
            }

            override fun onFailure(call: Call<CharacterDataWrapper>, t: Throwable) {
                t.message
            }
        })
    }

    private fun createImage(character: Character, imageView: ImageView) {
        var url = "${character.thumbnail.path}/landscape_large.${character.thumbnail.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(imageView)
    }
}
