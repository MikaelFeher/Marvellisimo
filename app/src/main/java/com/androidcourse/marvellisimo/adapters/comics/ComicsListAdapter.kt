package com.androidcourse.marvellisimo.adapters.comics

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.activities.comics.ComicsActivity
import com.androidcourse.marvellisimo.helpers.CustomItemClickListener
import com.androidcourse.marvellisimo.models.comics.Comics
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comics_list_item.view.*

class ComicsListAdapter(private val comicsList: List<Comics>, private val context: Context) : RecyclerView.Adapter<ComicsListAdapter.CustomViewHolder>() {
    val mContext = context

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
        println("Title: ${comic.title}")
        holder.comicTitle.text = comic.title
        createImage(comic, holder)

        holder.setOnCustomItemClickListener(object : CustomItemClickListener {
            override fun onCustomClickListener(view: View, pos: Int) {
                val i = Intent(mContext, ComicsActivity::class.java)
                i.putExtra("comicId", comic.id)
                mContext.startActivity(i)
            }
        })
    }

    private fun createImage(comic: Comics, holder: CustomViewHolder) {
        var url = "${comic.thumbnail.path}/landscape_large.${comic.thumbnail.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(holder.comicImg)
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val comicTitle: TextView = view.tv_comics_list_item_title
        val comicImg: ImageView = view.iv_comics_list_item_Thumbnail
        var customItemClickListener: CustomItemClickListener? = null

        init {
            view.setOnClickListener{
                it.setBackgroundColor( Color.GREEN)
            }
        }

        fun setOnCustomItemClickListener(itemClickListener: CustomItemClickListener) {
            this.customItemClickListener = itemClickListener
        }

        override fun onClick(itemView: View?) {
            this.customItemClickListener!!.onCustomClickListener(itemView!!, adapterPosition)
        }

    }
}

