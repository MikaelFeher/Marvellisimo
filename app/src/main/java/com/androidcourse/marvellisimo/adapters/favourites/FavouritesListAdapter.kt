package com.androidcourse.marvellisimo.adapters.favourites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.fragments.characters.CharacterFragment
import com.androidcourse.marvellisimo.fragments.comics.ComicFragment
import com.androidcourse.marvellisimo.helpers.FragmentHandler
import com.androidcourse.marvellisimo.models.Realm.Favourite
import com.androidcourse.marvellisimo.services.FavouriteService
import com.androidcourse.marvellisimo.services.RealmService
import com.squareup.picasso.Picasso
import io.realm.RealmResults
import kotlinx.android.synthetic.main.character_list_item.view.*
import kotlinx.android.synthetic.main.favourites_list_item.view.*

class FavouritesListAdapter(val favouritesList: RealmResults<Favourite>) :
    RecyclerView.Adapter<FavouritesListAdapter.CustomViewHolder>() {

    private val isCharacterList = if (favouritesList.isNotEmpty()) favouritesList[0]!!.isCharacter else false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.favourites_list_item, parent, false)
        return CustomViewHolder(cellForRow, isCharacterList!!)
    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val favourite = favouritesList[position]
        val isFavourite = true

        holder.name.text = favourite!!.name
        holder.itemView.tag = favourite!!.id
        createImage(favourite, holder)

        FavouriteService.setFavourite(isFavourite, holder.favouriteToggle)

        // TODO: Update favouritesList on removing favourite...

        holder.favouriteToggle.setOnClickListener {
            val tempFavourite = Favourite().apply {
                id = favourite.id
                name = favourite.name
                isCharacter = favourite.isCharacter
                image = favourite.image
            }
            RealmService.removeFavourite(favourite.id!!)
            FavouriteService.removeFavouriteSnackBar(tempFavourite!!, it, this)
            updateFavouritesList()
        }
    }

    private fun updateFavouritesList() {
        this.notifyDataSetChanged()
    }

    private fun createImage(favourite: Favourite, holder: CustomViewHolder) {
        var url = "${favourite.image!!.path}/portrait_large.${favourite.image!!.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(holder.img)
    }

    class CustomViewHolder(view: View, isCharacterList: Boolean) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val name: TextView = view.tv_favorites_list_item_name
        val img: ImageView = view.iv_favorites_list_item_image
        val favouriteToggle: ToggleButton = view.tb_favourites_list_item_favourite_toggle

        init {
            view.setOnClickListener() {
                val context = it.context
                val mActivity: FragmentHandler = context as FragmentHandler

                if (isCharacterList!!) {
                    val characterId = it.tag
                    mActivity.setNextFragment(CharacterFragment.create(characterId.toString()))
                } else {
                    val comicId = it.tag
                    mActivity.setNextFragment(ComicFragment.create(comicId.toString()))
                }
            }
        }

        override fun onClick(p0: View?) {
        }
    }
}