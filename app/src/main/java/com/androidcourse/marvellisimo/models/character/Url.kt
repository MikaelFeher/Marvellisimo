package com.androidcourse.marvellisimo.models.character

import io.realm.RealmModel

data class Url(
    val type: String,
    val url: String
) : RealmModel