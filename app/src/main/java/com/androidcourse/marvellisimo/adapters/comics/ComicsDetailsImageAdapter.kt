package com.androidcourse.marvellisimo.adapters.comics

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.models.comics.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comics_images_list_item.view.*

class ComicsDetailsImageAdapter(imagesList: List<Image>) : RecyclerView.Adapter<ComicsDetailsImageAdapter.CustomViewHolder>() {

    private val mImagesList = imagesList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.comics_images_list_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return mImagesList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var image = mImagesList[position]
        createImage(image, holder)
    }

    private fun createImage(image: Image, holder: CustomViewHolder) {
        var url = "${image.path}/landscape_large.${image.extension}"
        url = url.replace("http", "https")
        Picasso.get().load(url).into(holder.comicDetailsImageListItem)
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val comicDetailsImageListItem : ImageView = view.iv_comics_image_list_item_image
    }
}