package com.androidcourse.marvellisimo

import android.app.Application
import com.androidcourse.marvellisimo.dto.DataHandler
import io.realm.Realm
import io.realm.RealmConfiguration

class Marvellisimo: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("marvellisimo")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(configuration)

        println("Application onCreate")

    }
}