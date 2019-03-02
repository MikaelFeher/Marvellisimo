package com.androidcourse.marvellisimo.models.comics

import com.androidcourse.marvellisimo.helpers.FavouriteHandler
import com.androidcourse.marvellisimo.models.Realm.Favourite
import com.androidcourse.marvellisimo.models.Realm.RealmImage

data class Comics(
    val characters: Characters,
    val collectedIssues: List<Any>,
    val collections: List<Any>,
    val creators: Creators,
    val dates: List<Date>,
    val description: String,
    val diamondCode: String,
    val digitalId: Int,
    val ean: String,
    val events: Events,
    val format: String,
    val id: Int,
    val images: List<Image>,
    val isbn: String,
    val issn: String,
    val issueNumber: Int,
    val modified: String,
    val pageCount: Int,
    val prices: List<Price>,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val textObjects: List<TextObject>,
    val thumbnail: Thumbnail,
    val title: String,
    val upc: String,
    val urls: List<Url>,
    val variantDescription: String,
    val variants: List<Any>
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
            this.isCharacter = false
            this.name = name
            this.image = newImage
        }
        return newFavorite
    }
}