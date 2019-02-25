package com.androidcourse.marvellisimo.fragments.characters

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.models.character.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character.*


/**
 * A simple [Fragment] subclass.
 *
 */
class CharacterFragment : Fragment() {

    companion object {
        fun create(id: String): CharacterFragment {
            val fragment = CharacterFragment()
            val args = Bundle()
            args.putString("id", id)
            fragment.arguments = args
            return fragment
        }
    }

    private var characterId: String? = null
    private lateinit var viewItem: View
    private lateinit var labelForComicsList: TextView
    private lateinit var characterDetailsComicsList: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterId = arguments!!.getString("id")
        labelForComicsList = tv_fragment_label_for_rv_character_details_comics_list
        characterDetailsComicsList = rv_fragment_character_details_comics_list

        characterId?.let{
            DataHandler.getCharacterById(it)
            DataHandler.findComicsByCharacter(it)
        }

        DataHandler.character.observe(this, Observer {
            if (it == null) {
                pb_fragment_character_progressbar.visibility = View.VISIBLE
            } else {
                pb_fragment_character_progressbar.visibility = View.GONE
                setCharacterViewFields(it!!)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_character, container, false)
        return viewItem
    }

    override fun onDestroy() {
        super.onDestroy()
        DataHandler.character = MutableLiveData()
    }

    private fun setCharacterViewFields(character: Character) {
        tv_fragment_character_name.text = character.name
        tv_fragment_character_description.text = if (character.description.isNotEmpty()) character.description else "No description available..."
        createImage(character, iv_fragment_character_image)
    }

    private fun createImage(character: Character, imageView: ImageView) {
        var url = "${character.thumbnail.path}/portrait_large.${character.thumbnail.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(imageView)
    }

}
