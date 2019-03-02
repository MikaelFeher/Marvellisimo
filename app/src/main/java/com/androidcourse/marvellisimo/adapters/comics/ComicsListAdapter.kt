package com.androidcourse.marvellisimo.adapters.comics

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.fragments.comics.ComicFragment
import com.androidcourse.marvellisimo.helpers.FragmentHandler
import com.androidcourse.marvellisimo.models.Realm.Favourite
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.services.RealmService
import com.squareup.picasso.Picasso
import io.realm.RealmResults
import kotlinx.android.synthetic.main.comics_list_item.view.*

class ComicsListAdapter(private val comicsList: List<Comics>) : RecyclerView.Adapter<ComicsListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.comics_list_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
       return comicsList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val comic = comicsList[position]
        var isFavourite = checkIfFavourite(comic.id)

        holder.comicTitle.text = comic.title
        holder.itemView.tag = comic.id
        createImage(comic, holder)

        setFavourite(isFavourite, holder)

        holder.favouriteToggle.setOnClickListener {

            if (isFavourite) {
                RealmService.removeFavourite(comic.id)
            } else {
                var newFavourite = createFavorite(comic)
                RealmService.addFavourite(newFavourite)
            }

            isFavourite = checkIfFavourite(comic.id)
            setFavourite(isFavourite, holder)
        }
    }

    private fun createFavorite(comic: Comics) =
        comic.transformToFavourite(
            comic.id,
            comic.title,
            comic.thumbnail.path!!,
            comic.thumbnail.extension!!
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

    private fun createImage(comic: Comics, holder: CustomViewHolder) {
        val imageList = comic.images
        var imgPath = if (imageList.isNotEmpty()) imageList[0].path else comic.thumbnail.path
        var imgExtension = if (imageList.isNotEmpty()) imageList[0].extension else comic.thumbnail.extension
        var url = "$imgPath/portrait_large.$imgExtension"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(holder.comicImg)
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val comicTitle: TextView = view.tv_comics_list_item_title
        val comicImg: ImageView = view.iv_comics_list_item_Thumbnail
        val favouriteToggle: ToggleButton = view.tb_comics_list_item_favourite_toggle
        val starEmpty: Int = R.drawable.favourite_empty
        val starFilled: Int = R.drawable.favourite_filled

        init {
            view.setOnClickListener {
                val context = it.context
                val mActivity : FragmentHandler = context as FragmentHandler
                val comicId = it.tag

                mActivity.setNextFragment(ComicFragment.create(comicId.toString()))
            }
        }

        override fun onClick(itemView: View?) {
        }

    }
}

