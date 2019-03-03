package com.androidcourse.marvellisimo.adapters.character

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.fragments.characters.CharacterFragment
import com.androidcourse.marvellisimo.helpers.FragmentHandler
import com.androidcourse.marvellisimo.models.character.Character
import com.androidcourse.marvellisimo.services.FavouriteService
import com.androidcourse.marvellisimo.services.RealmService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_list_item.view.*

class CharacterListAdapter(private var charactersList: List<Character>, recyclerView: RecyclerView, isSearchResult: Boolean? = false) :
    RecyclerView.Adapter<CharacterListAdapter.CustomViewHolder>() {

    var isLoading = false

    init {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = layoutManager.itemCount
                if (!isLoading && visibleItemCount + pastVisibleItem >= total) {
                    isLoading = true
                    addMore(isSearchResult)
                    isLoading = false
                }
            }
        })
    }

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
        var isFavourite = FavouriteService.checkIfFavourite(character.id)

        holder.name.text = character.name
        holder.itemView.tag = character.id
        createImage(character, holder)
        FavouriteService.setFavourite(isFavourite, holder.favouriteToggle)
        holder.favouriteToggle.setOnClickListener {

            if (isFavourite) {
                val tempFavourite = character.transformToFavourite(character.id, character.name, character.thumbnail.path!!, character.thumbnail.extension!!)
                RealmService.removeFavourite(character.id)
                FavouriteService.removeFavouriteSnackBar(tempFavourite!!, it)
            } else {
                var newFavourite = FavouriteService.createFavoriteCharacter(character)
                RealmService.addFavourite(newFavourite, holder.favouriteToggle.context)
                FavouriteService.addFavouriteSnackBar(newFavourite, it)
            }

            isFavourite = FavouriteService.checkIfFavourite(character.id)
            FavouriteService.setFavourite(isFavourite, it)
        }
    }

    fun addMore(isSearchResult: Boolean?) {
        DataHandler.getMoreCharacters()
        if (isSearchResult!!) {
            charactersList = DataHandler.characterSearchResult!!.value!!
        } else{
            charactersList = DataHandler.characters.value!!
        }
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

