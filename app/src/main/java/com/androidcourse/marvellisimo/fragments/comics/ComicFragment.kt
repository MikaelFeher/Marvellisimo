package com.androidcourse.marvellisimo.fragments.comics


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.DataHandler

class ComicFragment : Fragment() {

    companion object {
        fun create(id: String): ComicFragment {
            val fragment = ComicFragment()
            val args = Bundle()
            args.putString("id", id)
            fragment.arguments = args
            return fragment
        }
    }

    private var viewItem: View? = null
    private var comicId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comicId = arguments!!.getString("id")

        comicId?.let{
            DataHandler.getComic(it)
        }

        DataHandler.comic.observe(this, Observer {
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
        return inflater.inflate(R.layout.fragment_comic, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        DataHandler.character.postValue(null)
    }
}
