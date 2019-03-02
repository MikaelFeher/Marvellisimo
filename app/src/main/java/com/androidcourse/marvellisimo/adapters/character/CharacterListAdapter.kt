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
import com.androidcourse.marvellisimo.models.Realm.Favourite
import com.androidcourse.marvellisimo.models.character.Character
import com.androidcourse.marvellisimo.services.RealmService
import com.squareup.picasso.Picasso
import io.realm.RealmResults
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
        var isFavourite = checkIfFavourite(character.id)

        holder.name.text = character.name
        holder.itemView.tag = character.id
        createImage(character, holder)

        setFavourite(isFavourite, holder)

        holder.favouriteToggle.setOnClickListener {

            if (isFavourite) {
                RealmService.removeFavourite(character.id)
            } else {
                var newFavourite = createFavorite(character)
                RealmService.addFavourite(newFavourite)
            }

            isFavourite = checkIfFavourite(character.id)
            setFavourite(isFavourite, holder)
        }

    }

    private fun createFavorite(character: Character) =
        character.transformToFavourite(
            character.id,
            character.name,
            character.thumbnail.path!!,
            character.thumbnail.extension!!
        )

    private fun setFavourite(
        isFavorite: Boolean,
        holder: CustomViewHolder
    ) {
        if (isFavorite) holder.favouriteToggle.setBackgroundResource(holder.starFilled)
        else holder.favouriteToggle.setBackgroundResource(holder.starEmpty)
    }

    private fun checkIfFavourite(id: Int): Boolean {
        val favourite: RealmResults<Favourite>? = RealmService.findById(id)
        return favourite!!.size == 1
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
        val starEmpty: Int = R.drawable.favourite_empty
        val starFilled: Int = R.drawable.favourite_filled

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

