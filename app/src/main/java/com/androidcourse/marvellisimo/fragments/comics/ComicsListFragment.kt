package com.androidcourse.marvellisimo.fragments.comics


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.comics.ComicsListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import kotlinx.android.synthetic.main.fragment_comics_list.view.*

class ComicsListFragment : Fragment() {

    private var viewItem : View? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var comicsList = DataHandler.comics!!
        val comicsListFragment = viewItem!!.rv_fragment_comics_list
        comicsListFragment.adapter = ComicsListAdapter(comicsList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_comics_list, container, false)
        return viewItem
    }


}
