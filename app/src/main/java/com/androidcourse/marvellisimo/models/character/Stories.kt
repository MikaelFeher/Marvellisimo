package com.androidcourse.marvellisimo.models.character

import io.realm.RealmList

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: RealmList<Any>,
    val returned: Int
)