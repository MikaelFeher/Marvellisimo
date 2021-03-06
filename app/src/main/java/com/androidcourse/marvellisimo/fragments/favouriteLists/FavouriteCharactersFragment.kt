package com.androidcourse.marvellisimo.fragments.favouriteLists

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.favourites.FavouritesListAdapter
import com.androidcourse.marvellisimo.services.RealmService
import kotlinx.android.synthetic.main.fragment_favourite_characters.view.*

class FavouriteCharactersFragment : Fragment() {
    private var viewItem: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favouriteCharactersFragment = viewItem!!.rv_fragment_favourite_characters
        val noContent = viewItem!!.tv_fragment_favourite_characters_no_content

        val favouriteCharacters = RealmService.getFavoriteCharacters()

        if (favouriteCharacters.isEmpty()) {
            noContent.visibility = View.VISIBLE
        } else {
            noContent.visibility = View.GONE
            favouriteCharactersFragment.adapter = FavouritesListAdapter(favouriteCharacters, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_favourite_characters, container, false)
        return viewItem
    }
}
