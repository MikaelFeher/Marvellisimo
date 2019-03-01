package com.androidcourse.marvellisimo.models.character

import io.realm.RealmObject

open class Thumbnail: RealmObject() {
    var extension: String? = null
    var path: String? = null
}