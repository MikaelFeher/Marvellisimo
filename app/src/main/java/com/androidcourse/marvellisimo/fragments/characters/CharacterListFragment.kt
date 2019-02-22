package com.androidcourse.marvellisimo.fragments.characters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.character.CharacterListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import kotlinx.android.synthetic.main.fragment_character_list.view.*

class CharacterListFragment : Fragment() {
    private var viewItem : View? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var characterList = DataHandler.characters!!
        val characterListFragment = viewItem!!.rv_fragment_character_list
        characterListFragment.adapter = CharacterListAdapter(characterList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_character_list, container, false)
        return viewItem
    }
}
