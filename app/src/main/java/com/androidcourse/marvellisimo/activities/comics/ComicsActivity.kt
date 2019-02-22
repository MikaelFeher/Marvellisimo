package com.androidcourse.marvellisimo.activities.comics

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.comics.ComicsDetailsImageAdapter
import com.androidcourse.marvellisimo.dto.comics.ComicsDataWrapper
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.models.comics.Image
import com.androidcourse.marvellisimo.services.comics.ComicsServiceHandler
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comics.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicsActivity : AppCompatActivity() {
    private var comic: Comics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics)
        var comicId : Any = 0


        if (intent.hasExtra("comicId")){
            comicId = intent.extras.get("comicId")
        }

        getComic(comicId)
    }

    private fun getComic(comicId: Any) {
        ComicsServiceHandler.getComicById(comicId.toString()).enqueue(object : Callback<ComicsDataWrapper> {
            override fun onResponse(call: Call<ComicsDataWrapper>, response: Response<ComicsDataWrapper>) {
                comic = response.body()!!.data.results[0]
                val imagesList : List<Image> = response.body()!!.data.results[0].images
                val comicsDetailsImageList = rv_comics_details_images_list
                val labelForImagesList = tv_label_for_rv_comics_details_images_list

                if (imagesList.size < 2) labelForImagesList.visibility = View.GONE

                setComicsViewFields(comic!!)
                showData(imagesList, comicsDetailsImageList, this@ComicsActivity)
            }

            override fun onFailure(call: Call<ComicsDataWrapper>, t: Throwable) {
                t.message
            }

        })
    }

    private fun setComicsViewFields(comic: Comics) {
        tv_fragment_comics_details_title.text = comic.title
        tv_comics_details_description.text = comic.description ?: "No description available..."
        createImage(comic)
    }

    private fun createImage(comic: Comics) {
        var url = "${comic.thumbnail.path}/landscape_large.${comic.thumbnail.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(iv_fragment_comics_thumbnail)
    }

    private fun showData(imagesList: List<Image>, comicsDetailsImageList: RecyclerView, context: Context) {
        comicsDetailsImageList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ComicsDetailsImageAdapter(imagesList, context)
        }
    }
}
