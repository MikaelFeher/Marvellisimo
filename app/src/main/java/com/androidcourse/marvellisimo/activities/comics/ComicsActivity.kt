package com.androidcourse.marvellisimo.activities.comics

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.comics.ComicsDataWrapper
import com.androidcourse.marvellisimo.models.comics.Comics
import com.androidcourse.marvellisimo.services.comics.ComicsServiceHandler
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
                setComicsViewFields(comic!!)
            }

            override fun onFailure(call: Call<ComicsDataWrapper>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun setComicsViewFields(comic: Comics) {
        tv_comics_title.text = comic.title
        tv_comics_description.text = comic.description.toString()

    }
}
