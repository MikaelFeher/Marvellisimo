package com.androidcourse.marvellisimo.fragments.characters

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.character.CharacterListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import kotlinx.android.synthetic.main.fragment_character_list.*
import kotlinx.android.synthetic.main.fragment_character_list.view.*

class CharacterListFragment : Fragment() {
    private var viewItem: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterListFragment = viewItem!!.rv_fragment_character_list

        DataHandler.characters!!.observe(this, Observer {
            while (it == null) {
                pb_fragment_character_list_progressbar.visibility = View.VISIBLE
            }
            pb_fragment_character_list_progressbar.visibility = View.GONE
            characterListFragment.adapter = CharacterListAdapter(it)

        })
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
