package com.androidcourse.marvellisimo.adapters.character

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.fragments.characters.CharacterFragment
import com.androidcourse.marvellisimo.helpers.FragmentHandler
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

        holder.favouriteToggle.setOnClickListener {

            if (holder.favouriteToggle.isChecked) it.setBackgroundResource(holder.starFilledId)
            else it.setBackgroundResource(holder.starEmptyId)
        }

//        holder.favourite.setOnClickListener {
//
//            println("Star clicked: ${it.id}")
//            if (holder.favourite.resources.assets. == holder.starEmptyId) holder.favourite.setImageResource(holder.starFilledId)
//            else holder.favourite.setImageResource(holder.starEmptyId)
//        }
    }

    private fun createImage(character: Character, holder: CustomViewHolder) {
        var url = "${character.thumbnail.path}/portrait_large.${character.thumbnail.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(holder.img)
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val name: TextView = view.tvName
        val img: ImageView = view.ivThumbnail
        val favouriteToggle: ToggleButton = view.tb_character_list_item_favourite_toggle
        val starEmptyId: Int = R.drawable.favourite_empty
        val starFilledId: Int = R.drawable.favourite_filled

        init {
            view.setOnClickListener{
                val context = it.context
                val mActivity : FragmentHandler = context as FragmentHandler
                val characterId = it.tag

                mActivity.setNextFragment(CharacterFragment.create(characterId.toString()))
            }
        }

        override fun onClick(p0: View?) {
        }


    }
}

