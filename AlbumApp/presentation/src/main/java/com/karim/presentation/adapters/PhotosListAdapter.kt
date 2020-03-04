package com.karim.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karim.presentation.R
import com.karim.presentation.model.PhotoAlbum
import kotlinx.android.synthetic.main.photo_list_item.view.*
import kotlin.collections.ArrayList


class PhotosListAdapter(
    val context: Context,
    val photosList : List<PhotoAlbum>,
    val itemListener: PhotoItemListener
    ) : RecyclerView.Adapter<PhotosListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.photo_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = photosList.size

    fun setList(photos: List<PhotoAlbum>) {
        (photosList as ArrayList<PhotoAlbum>).clear()
        photosList.addAll(photos)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val photo = photosList.get(position)

        with(holder) {
            titleText.text = photo.title
            urlText.text = photo.url


            //doesn't work for this url
            /*val url1 = "https://via.placeholder.com/300.png"

            CoroutineScope(Dispatchers.IO).launch {

                //it works for this one
                //val url2 ="http://books.google.com/books/content?id=aPqFDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"

                val url = URL(url1)

                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())

                withContext(Main){

                    photoImage.setImageBitmap(bmp)
                }
            }*/

            Glide.with(context)
                .load(photo.url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(photoImage)
        }



        holder.itemView.setOnClickListener {
            itemListener.onPhotoItemClick(photo)
        }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val titleText = itemView.title
        val photoImage = itemView.photoImage
        val urlText = itemView.url
    }


    interface PhotoItemListener {
        fun onPhotoItemClick(photo: PhotoAlbum)
    }

}

