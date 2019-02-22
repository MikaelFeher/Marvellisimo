package com.androidcourse.marvellisimo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.DataHandler


/**
 * A simple [Fragment] subclass.
 *
 */
class CharacterFragment : Fragment() {
    private var viewItem : View? = null
    private var characterId : Any = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mIntent = activity!!.intent

        if (mIntent.hasExtra("characterId")){
            characterId = mIntent.extras.get("characterId")
        }
        val character = DataHandler.getCharacter(characterId)

        println("character from characterFragment: $character")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_character, container, false)
        return viewItem
    }


}
