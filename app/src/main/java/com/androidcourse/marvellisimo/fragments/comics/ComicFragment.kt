package com.androidcourse.marvellisimo.fragments.comics


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView

import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.character.CharacterListAdapter
import com.androidcourse.marvellisimo.adapters.comics.ComicsDetailsImageAdapter
import com.androidcourse.marvellisimo.adapters.comics.ComicsListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.models.comics.Characters
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.models.comics.Image
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

    private var viewItem: View? = null
    private var comicId: String? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var labelForCharactersList: TextView
    private lateinit var comicDetailsCharacterList: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comicId = arguments!!.getString("id")
        progressBar = pb_fragment_comic_progressbar
        labelForCharactersList = tv_fragment_label_for_rv_comics_details_characters_list
        comicDetailsCharacterList = rv_fragment_comics_details_characters_list

        comicId?.let{
            DataHandler.getComicById(it)
            DataHandler.findCharacterByComic(it)
        }

        DataHandler.comic.observe(this, Observer {
            if (it == null) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                setComicsViewFields(it!!)
                setComicFragmentCoverArtsList(it.images)
            }
        })

        DataHandler.charactersByComic!!.observe(this, Observer {
            if (it.isNullOrEmpty()){
                labelForCharactersList.visibility = View.VISIBLE
            } else {
                labelForCharactersList.visibility = View.GONE
                comicDetailsCharacterList.adapter = CharacterListAdapter(it)
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
    }

    private fun createImage(comic: Comics) {
        var url = "${comic.thumbnail.path}/portrait_large.${comic.thumbnail.extension}"
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
        DataHandler.comic.postValue(null)
        DataHandler.charactersByComic!!.postValue(null)
    }
}
