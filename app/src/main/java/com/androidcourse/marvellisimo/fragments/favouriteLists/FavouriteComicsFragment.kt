package com.androidcourse.marvellisimo.fragments.favouriteLists

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.favourites.FavouritesListAdapter
import com.androidcourse.marvellisimo.services.RealmService
import kotlinx.android.synthetic.main.fragment_favourite_comics.*
import kotlinx.android.synthetic.main.fragment_favourite_comics.view.*

class FavouriteComicsFragment : Fragment() {
    private var viewItem: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favouriteComicsFragment = viewItem!!.rv_fragment_favourite_comics
        val noContentMsg = tv_fragment_favourite_comics_no_content

        val favouriteComics = RealmService.getFavoriteComics()
        if (favouriteComics.isEmpty()) {
            noContentMsg.visibility = View.VISIBLE
        } else {
            noContentMsg.visibility = View.GONE
            favouriteComicsFragment.adapter = FavouritesListAdapter(favouriteComics, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_favourite_comics, container, false)
        return viewItem
    }
}
