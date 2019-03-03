package com.androidcourse.marvellisimo.fragments.comics


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.comics.ComicsListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import kotlinx.android.synthetic.main.fragment_comics_list.*
import kotlinx.android.synthetic.main.fragment_comics_list.view.*

class ComicsListFragment : Fragment() {

    private var viewItem: View? = null
    private var adapter: ComicsListAdapter? = null
    lateinit var progressBar: ProgressBar
    private lateinit var comicsListFragment: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        comicsListFragment = viewItem!!.rv_fragment_comics_list
        comicsListFragment.layoutManager = LinearLayoutManager(this.context)
        progressBar = pb_fragment_comics_list_progressbar


        populateComicsList()
    }

    private fun populateComicsList() {
        DataHandler.comics!!.observe(this, Observer {
            while (it!!.isEmpty()) {
                progressBar.visibility = View.VISIBLE
            }
            progressBar.visibility = View.GONE
            if (adapter == null) {
                adapter = ComicsListAdapter(it, comicsListFragment)
                comicsListFragment.adapter = adapter
            }
            adapter!!.notifyDataSetChanged()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_comics_list, container, false)
        return viewItem
    }

    override fun onResume() {
        super.onResume()
        populateComicsList()
    }

    override fun onPause() {
        super.onPause()
        adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
        DataHandler.comics = MutableLiveData()
        adapter = null
    }
}
