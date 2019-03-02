package com.androidcourse.marvellisimo.services

import android.support.design.widget.Snackbar
import android.view.View
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.helpers.UndoListener
import com.androidcourse.marvellisimo.models.Realm.Favourite
import com.androidcourse.marvellisimo.models.character.Character
import com.androidcourse.marvellisimo.models.comics.Comics
import io.realm.RealmResults

object FavouriteService {

    private var starEmpty: Int = R.drawable.favourite_empty
    private var starFilled: Int = R.drawable.favourite_filled

    fun setFavourite(isFavourite: Boolean, favouriteToggle: View) {
        if (isFavourite) favouriteToggle.setBackgroundResource(starFilled!!)
        else favouriteToggle.setBackgroundResource(starEmpty!!)
    }

    fun checkIfFavourite(id: Int): Boolean {
        val favourite: RealmResults<Favourite>? = RealmService.findById(id)
        return favourite!!.size == 1
    }

    fun removeFavouriteSnackBar(favourite: Favourite, view: View) {
        Snackbar.make(view, "${favourite.name} was removed from favourites", Snackbar.LENGTH_LONG)
            .setAction("Undo", UndoListener(favourite, view)).show()
    }

    fun addFavouriteSnackBar(favourite: Favourite, view: View) {
        Snackbar.make(view, "${favourite.name} was added to favourites", Snackbar.LENGTH_LONG)
            .show()
    }

    fun createFavoriteComic(comic: Comics) =
        comic.transformToFavourite(
            comic.id,
            comic.title,
            comic.thumbnail.path!!,
            comic.thumbnail.extension!!
        )

    fun createFavoriteCharacter(character: Character) =
        character.transformToFavourite(
            character.id,
            character.name,
            character.thumbnail.path!!,
            character.thumbnail.extension!!
        )
}