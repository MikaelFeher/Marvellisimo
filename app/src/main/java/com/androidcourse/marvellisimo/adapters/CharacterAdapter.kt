package com.androidcourse.marvellisimo.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.CharacterDataContainer
import com.androidcourse.marvellisimo.dto.CharacterDataWrapper
import com.androidcourse.marvellisimo.models.Character
import com.androidcourse.marvellisimo.retrofit.MarvelService
import com.androidcourse.marvellisimo.retrofit.MarvelServiceHandler
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterAdapter(private val charactersList: List<Character>) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.character_list_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        println("charactersList.size ${charactersList.size}")
        val character = charactersList[position]
        holder.name.text = character.name
        createImage(character, holder)
    }

    fun createImage(character: Character, holder: CustomViewHolder){
        var url = "${character.thumbnail.path}/landscape_large.${character.thumbnail.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(holder.img)
    }

}

class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.tvName
    val img: ImageView = view.ivThumbnail
}