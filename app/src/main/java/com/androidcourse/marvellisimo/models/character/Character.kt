package com.androidcourse.marvellisimo.models.character

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass("Character")
open class Character: RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var comics: Comics? = null
    var description: String? = null
    var events: Events? = null
    var modified: String? = null
    var name: String? = null
    var resourceURI: String? = null
    var series: Series? = null
    var stories: Stories? = null
    var thumbnail: Thumbnail? = null
    var urls: RealmList<Url>? = null
}