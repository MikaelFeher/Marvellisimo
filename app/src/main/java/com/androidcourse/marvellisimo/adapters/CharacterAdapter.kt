package com.androidcourse.marvellisimo.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.CharacterDataContainer
import com.androidcourse.marvellisimo.dto.CharacterDataWrapper
import com.androidcourse.marvellisimo.models.Character
import com.androidcourse.marvellisimo.retrofit.MarvelService
import com.androidcourse.marvellisimo.retrofit.MarvelServiceHandler
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterAdapter: RecyclerView.Adapter<CustomViewHolder>() {

    val list = MarvelServiceHandler.getAllCharacters()


    override fun getItemCount(): Int {
        return 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.character_list_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        println("charactersList.size ${}")
//        holder?.view?.tvName?.text =

    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}