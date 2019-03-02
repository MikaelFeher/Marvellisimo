package com.androidcourse.marvellisimo.helpers

import android.support.design.widget.Snackbar
import android.view.View
import com.androidcourse.marvellisimo.models.Realm.Favourite
import com.androidcourse.marvellisimo.services.FavouriteService
import com.androidcourse.marvellisimo.services.RealmService

class UndoListener(val favourite: Favourite, val ItemView: View?): View.OnClickListener {

    override fun onClick(view: View?) {
        RealmService.addFavourite(favourite)
        FavouriteService.setFavourite(true, ItemView!!)
        Snackbar.make(view!!, "${favourite.name} is still a favourite", Snackbar.LENGTH_LONG)
            .show()
    }
}