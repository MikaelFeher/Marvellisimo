package com.androidcourse.marvellisimo.models.Realm

import com.androidcourse.marvellisimo.models.character.Thumbnail
import io.realm.RealmObject

open class Favourite: RealmObject() {
    var name: String? = null
    var id: Int? = null
    var isCharacter: Boolean? = false
    var image: RealmImage? = null
}