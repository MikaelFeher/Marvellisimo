package com.androidcourse.marvellisimo.fragments.characters

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.character.CharacterListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import kotlinx.android.synthetic.main.fragment_character_list.*
import kotlinx.android.synthetic.main.fragment_character_list.view.*

class CharacterListFragment : Fragment() {
    private var viewItem: View? = null
    private var adapter: CharacterListAdapter? = null
    private var isLoading = false
    lateinit var progressBar: ProgressBar
    private lateinit var characterListFragment: RecyclerView
    private lateinit var loadMoreButton: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterListFragment = viewItem!!.rv_fragment_character_list
        layoutManager = LinearLayoutManager(this.context)
        characterListFragment.layoutManager = layoutManager
        progressBar = pb_fragment_character_list_progressbar

        populateCharactersList()

    }

    private lateinit var layoutManager: LinearLayoutManager

    private fun populateCharactersList() {
        DataHandler.characters.observe(this, Observer {
            while (it == null) {
                progressBar.visibility = View.VISIBLE
            }

            progressBar.visibility = View.GONE
            if (adapter == null) {
                adapter = CharacterListAdapter(it, characterListFragment)
                characterListFragment.adapter = adapter
            }
            adapter!!.notifyDataSetChanged()
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

    override fun onResume() {
        super.onResume()
        populateCharactersList()
    }

    override fun onPause() {
        super.onPause()
        adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
        DataHandler.characters = MutableLiveData()
        adapter = null
    }
}
