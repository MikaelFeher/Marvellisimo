package com.androidcourse.marvellisimo.adapters.character

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.activities.character.CharacterActivity
import com.androidcourse.marvellisimo.models.character.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterListAdapter(private val charactersList: List<Character>) :
    RecyclerView.Adapter<CharacterListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.character_list_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val character = charactersList[position]
        holder.name.text = character.name
        holder.itemView.tag = character.id
        createImage(character, holder)
    }

    private fun createImage(character: Character, holder: CustomViewHolder) {
        var url = "${character.thumbnail.path}/landscape_large.${character.thumbnail.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(holder.img)
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val name: TextView = view.tvName
        val img: ImageView = view.ivThumbnail

        init {
            view.setOnClickListener{
                val context = it.context
                val characterId = it.tag
                val i = Intent(context, CharacterActivity::class.java)
                i.putExtra("characterId", characterId.toString())
                context.startActivity(i)
            }
        }

        override fun onClick(p0: View?) {
        }


    }
}

