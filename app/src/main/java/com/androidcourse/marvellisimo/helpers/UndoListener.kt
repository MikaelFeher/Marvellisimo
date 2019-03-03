package com.androidcourse.marvellisimo.helpers

import android.support.design.widget.Snackbar
import android.view.View
import com.androidcourse.marvellisimo.adapters.favourites.FavouritesListAdapter
import com.androidcourse.marvellisimo.models.Realm.Favourite
import com.androidcourse.marvellisimo.services.FavouriteService
import com.androidcourse.marvellisimo.services.RealmService

class UndoListener(
    val favourite: Favourite,
    private val itemView: View?,
    private val adapter: FavouritesListAdapter? = null
): View.OnClickListener {

    override fun onClick(view: View?) {
        RealmService.addFavourite(favourite, itemView!!.context)
        FavouriteService.setFavourite(true, itemView!!)
//        adapter!!.notifyDataSetChanged()
        Snackbar.make(view!!, "${favourite.name} is still a favourite", Snackbar.LENGTH_LONG)
            .show()
    }
}