package com.androidcourse.marvellisimo.models.character

import com.androidcourse.marvellisimo.helpers.FavouriteHandler
import com.androidcourse.marvellisimo.models.Realm.Favourite
import com.androidcourse.marvellisimo.models.Realm.RealmImage

data class Character(
    val id: Int,
    val comics: Comics,
    val description: String,
    val events: Events,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
): FavouriteHandler {
    override fun transformToFavourite(id: Int?, name: String?, imagePath: String, imageExt: String): Favourite {
        var newFavorite = Favourite()
        var newImage = RealmImage()
        newImage.apply {
            path = imagePath
            extension = imageExt
        }
        newFavorite.apply {
            this.id = id
            this.isCharacter = true
            this.name = name
            this.image = newImage
        }
        return newFavorite
    }
}