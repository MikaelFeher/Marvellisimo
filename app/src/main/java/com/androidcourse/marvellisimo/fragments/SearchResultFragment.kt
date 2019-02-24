package com.androidcourse.marvellisimo.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.character.CharacterListAdapter
import com.androidcourse.marvellisimo.adapters.comics.ComicsListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
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

    private var viewItem: View? = null
    private var isCharacter: Boolean? = null
    lateinit var searchResultFragment: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCharacter = arguments!!.getBoolean("isCharacter")
        searchResultFragment = viewItem!!.rv_fragment_search_result

        if (isCharacter!!) {
            DataHandler.characterSearchResult!!.observe(this, Observer {
                searchResultFragment.adapter = CharacterListAdapter(it!!)
            })
        } else {
            DataHandler.comicSearchResult!!.observe(this, Observer {
                searchResultFragment.adapter = ComicsListAdapter(it!!)
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_search_result, container, false)
        return viewItem
    }

}
