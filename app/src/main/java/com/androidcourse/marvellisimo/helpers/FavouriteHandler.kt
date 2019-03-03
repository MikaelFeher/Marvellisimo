package com.androidcourse.marvellisimo.helpers

import com.androidcourse.marvellisimo.models.Realm.Favourite

interface FavouriteHandler {
    fun transformToFavourite(
        id: Int? = null,
        name: String? = null,
        imagePath: String,
        imageExt: String
    ): Favourite
}