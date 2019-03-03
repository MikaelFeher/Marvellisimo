package com.androidcourse.marvellisimo.models.Realm

import io.realm.RealmObject

open class RealmImage: RealmObject() {
    var extension: String? = null
    var path: String? = null
}