package com.androidcourse.marvellisimo.adapters.comics

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
import com.androidcourse.marvellisimo.fragments.comics.ComicFragment
import com.androidcourse.marvellisimo.helpers.FragmentHandler
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.services.FavouriteService
import com.androidcourse.marvellisimo.services.RealmService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comics_list_item.view.*

class ComicsListAdapter(private var comicsList: List<Comics>, recyclerView: RecyclerView, isSearchResult: Boolean? = false) : RecyclerView.Adapter<ComicsListAdapter.CustomViewHolder>() {

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
        val cellForRow = layoutInflater.inflate(R.layout.comics_list_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
       return comicsList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val comic = comicsList[position]
        var isFavourite = FavouriteService.checkIfFavourite(comic.id)

        holder.comicTitle.text = comic.title
        holder.itemView.tag = comic.id
        createImage(comic, holder)

        FavouriteService.setFavourite(isFavourite, holder.favouriteToggle)

        holder.favouriteToggle.setOnClickListener {

            if (isFavourite) {
                val tempFavourite = comic.transformToFavourite(comic.id, comic.title, comic.thumbnail.path, comic.thumbnail.extension)
                RealmService.removeFavourite(comic.id)
                FavouriteService.removeFavouriteSnackBar(tempFavourite, it)
            } else {
                var newFavourite = FavouriteService.createFavoriteComic(comic)
                RealmService.addFavourite(newFavourite, holder.favouriteToggle.context)
                FavouriteService.addFavouriteSnackBar(newFavourite, it)
            }

            isFavourite = FavouriteService.checkIfFavourite(comic.id)
            FavouriteService.setFavourite(isFavourite, it)
        }
    }

    fun addMore(isSearchResult: Boolean?) {
        DataHandler.getMoreComics()
        comicsList = if (isSearchResult!!) {
            DataHandler.comicSearchResult!!.value!!
        } else{
            DataHandler.comics!!.value!!
        }
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

