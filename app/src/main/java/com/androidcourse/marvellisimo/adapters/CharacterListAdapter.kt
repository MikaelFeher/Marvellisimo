package com.androidcourse.marvellisimo.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.androidcourse.marvellisimo.helpers.CustomItemClickListener
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.activities.CharacterActivity
import com.androidcourse.marvellisimo.models.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterListAdapter(private val charactersList: List<Character>, context: Context) :
    RecyclerView.Adapter<CustomViewHolder>() {

    var mContext = context

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

        holder.setOnCustomItemClickListener(object : CustomItemClickListener {
            override fun onCustomClickListener(view: View, pos: Int) {
                println("character id: ${character.id}")
                val i = Intent(mContext, CharacterActivity::class.java)
                i.putExtra("characterId", character.id)
                mContext.startActivity(i)
            }
        })
    }

    fun createImage(character: Character, holder: CustomViewHolder) {
        var url = "${character.thumbnail.path}/landscape_large.${character.thumbnail.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(holder.img)
    }

}

class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val name: TextView = view.tvName
    val img: ImageView = view.ivThumbnail
    var customItemClickListener: CustomItemClickListener? = null

    init {
        view.setOnClickListener(this)
    }

    fun setOnCustomItemClickListener(itemClickListener: CustomItemClickListener) {
        this.customItemClickListener = itemClickListener
    }

    override fun onClick(itemView: View?) {
        this.customItemClickListener!!.onCustomClickListener(itemView!!, adapterPosition)
    }

}