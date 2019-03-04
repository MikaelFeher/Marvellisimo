package com.androidcourse.marvellisimo.fragments

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.character.CharacterListAdapter
import com.androidcourse.marvellisimo.adapters.comics.ComicsListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import kotlinx.android.synthetic.main.fragment_character_list.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_search_result.view.*

class SearchResultFragment : Fragment() {

    companion object {
        fun create(type: Boolean): SearchResultFragment {
            val fragment = SearchResultFragment()
            val args = Bundle()
            args.putBoolean("isCharacter", type)
            fragment.arguments = args
            return fragment
        }
    }

    private var isCharacter: Boolean? = null
    private lateinit var viewItem: View
    private lateinit var searchResultFragment: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvNoContent: TextView
    private lateinit var loadMoreButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCharacter = arguments!!.getBoolean("isCharacter")
        searchResultFragment = viewItem.rv_fragment_search_result
        progressBar = pb_fragment_search_result_progressbar
        tvNoContent = tv_no_content_message
        loadMoreButton = btn_fragment_characters_list_load_more

        if (isCharacter!!) {
            populateCharacterSearchList()
        } else {
            populateComicsSearchList()
        }
    }

    private fun populateComicsSearchList() {
        DataHandler.comicSearchResult!!.observe(this, Observer {
            if (it == null) {
                progressBar.visibility = View.GONE
                tvNoContent.visibility = View.VISIBLE
            } else {
                while (it!!.isEmpty() && it != null) {
                    progressBar.visibility = View.VISIBLE
                }
                progressBar.visibility = View.GONE
                tvNoContent.visibility = View.GONE
                searchResultFragment.adapter = ComicsListAdapter(it, searchResultFragment, true)
            }
        })
    }

    private fun populateCharacterSearchList() {
        DataHandler.characterSearchResult!!.observe(this, Observer {
            if (it == null) {
                progressBar.visibility = View.GONE
                tvNoContent.visibility = View.VISIBLE
            } else {
                while (it!!.isEmpty() && it != null) {
                    progressBar.visibility = View.VISIBLE
                }
                progressBar.visibility = View.GONE
                tvNoContent.visibility = View.GONE
                searchResultFragment.adapter = CharacterListAdapter(it, searchResultFragment, loadMoreButton)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_search_result, container, false)
        return viewItem
    }

    override fun onDestroy() {
        super.onDestroy()
        DataHandler.characterSearchResult = MutableLiveData()
        DataHandler.comicSearchResult = MutableLiveData()
    }
}
