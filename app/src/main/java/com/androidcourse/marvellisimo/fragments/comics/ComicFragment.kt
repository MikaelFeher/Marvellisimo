package com.androidcourse.marvellisimo.fragments.comics


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ToggleButton

import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.character.CharacterListAdapter
import com.androidcourse.marvellisimo.adapters.comics.ComicsDetailsImageAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.models.comics.Image
import com.androidcourse.marvellisimo.services.FavouriteService
import com.androidcourse.marvellisimo.services.RealmService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_comic.*

class ComicFragment : Fragment() {

    companion object {
        fun create(id: String): ComicFragment {
            val fragment = ComicFragment()
            val args = Bundle()
            args.putString("id", id)
            fragment.arguments = args
            return fragment
        }
    }

    private var comicId: String? = null
    private var isFavourite: Boolean = false
    private lateinit var viewItem: View
    private lateinit var progressBar: ProgressBar
    private lateinit var labelForCharactersList: TextView
    private lateinit var comicDetailsCharacterList: RecyclerView
    private lateinit var favouriteToggle: ToggleButton


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comicId = arguments!!.getString("id")
        progressBar = pb_fragment_comic_progressbar
        labelForCharactersList = tv_fragment_label_for_rv_comics_details_characters_list
        comicDetailsCharacterList = rv_fragment_comics_details_characters_list
        favouriteToggle = tb_comic_fragment_favourite_toggle

        comicId?.let {
            DataHandler.getComicById(it)
            DataHandler.findCharacterByComic(it)
            isFavourite = FavouriteService.checkIfFavourite(it.toInt())
        }

        DataHandler.comic.observe(this, Observer {
            if (it == null) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                setComicsViewFields(it)
                setComicFragmentCoverArtsList(it.images)
            }
        })

        DataHandler.charactersByComic!!.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                labelForCharactersList.visibility = View.VISIBLE
            } else {
                labelForCharactersList.visibility = View.GONE
                comicDetailsCharacterList.adapter = CharacterListAdapter(it, comicDetailsCharacterList)
            }
        })
    }

    private fun setComicFragmentCoverArtsList(imagesList: List<Image>) {
        val comicsDetailsImageList = rv_fragment_comics_details_cover_arts_list
        val labelForImagesList = tv_fragment_label_for_rv_comics_details_cover_arts_list

        if (imagesList.size < 2) labelForImagesList.visibility = View.GONE
        comicsDetailsImageList.adapter = ComicsDetailsImageAdapter(imagesList)
    }

    private fun setComicsViewFields(comic: Comics) {
        tv_fragment_comics_details_title.text = comic.title
        tv_fragment_comics_details_description.text = comic.description ?: "No description available..."
        createImage(comic)
        FavouriteService.setFavourite(isFavourite, favouriteToggle)

        favouriteToggle.setOnClickListener {

            if (isFavourite) {
                val tempFavourite = comic.transformToFavourite(comic.id, comic.title, comic.thumbnail.path!!, comic.thumbnail.extension!!)
                RealmService.removeFavourite(comic.id)
                FavouriteService.removeFavouriteSnackBar(tempFavourite!!, it)
            } else {
                var newFavourite = FavouriteService.createFavoriteComic(comic)
                RealmService.addFavourite(newFavourite, this.context!!)
                FavouriteService.addFavouriteSnackBar(newFavourite, it)
            }

            isFavourite = FavouriteService.checkIfFavourite(comic.id)
            FavouriteService.setFavourite(isFavourite, it)
        }
    }

    private fun createImage(comic: Comics) {
        val imageList = comic.images
        var imgPath = if (imageList.isNotEmpty()) imageList[0].path else comic.thumbnail.path
        var imgExtension = if (imageList.isNotEmpty()) imageList[0].extension else comic.thumbnail.extension
        var url = "$imgPath/portrait_large.$imgExtension"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(iv_fragment_comics_thumbnail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_comic, container, false)
        return viewItem
    }

    override fun onDestroy() {
        super.onDestroy()
        DataHandler.comic = MutableLiveData()
        DataHandler.charactersByComic = MutableLiveData()
    }
}
