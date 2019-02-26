package com.androidcourse.marvellisimo.adapters.comics

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.fragments.comics.ComicFragment
import com.androidcourse.marvellisimo.helpers.FragmentHandler
import com.androidcourse.marvellisimo.models.comics.Comics
import com.squareup.picasso.Picasso
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
        holder.comicTitle.text = comic.title
        holder.itemView.tag = comic.id
        createImage(comic, holder)
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

