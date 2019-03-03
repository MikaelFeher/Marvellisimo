package com.androidcourse.marvellisimo.services

import android.content.Context
import android.widget.Toast
import com.androidcourse.marvellisimo.models.Realm.Favourite
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

object RealmService {

    lateinit var realm: Realm

    fun initializeRealm() {
        realm = Realm.getDefaultInstance()
    }

    fun killRealm() {
        realm.close()
    }

    fun getFavorites(): RealmResults<Favourite> {
        return realm.where<Favourite>().findAll()
    }

    fun getFavoriteCharacters(): RealmResults<Favourite> {
        return realm.where<Favourite>()
            .equalTo("isCharacter", true)
            .findAll()
    }

    fun addFavourite(favourite: Favourite, context: Context) {
        val exists: RealmResults<Favourite> = findById(favourite.id!!)
        if (exists.isEmpty()) {
            realm.beginTransaction()
            realm.insertOrUpdate(favourite)
            realm.commitTransaction()
        } else {
            Toast.makeText(context, "${favourite.name} is already a favourite", Toast.LENGTH_LONG)
        }

    }

    fun removeFavourite(id: Int) {
        realm.beginTransaction()
        realm.where<Favourite>()
            .equalTo("id", id)
            .findAll()
            .deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun findById(id: Int): RealmResults<Favourite> {
        realm.beginTransaction()
        var result = realm.where<Favourite>()
            .equalTo("id", id)
            .findAll()
        realm.commitTransaction()
        return result
    }

    fun getFavoriteComics(): RealmResults<Favourite> {
        return realm.where<Favourite>()
            .equalTo("isCharacter", false)
            .findAll()
    }
}