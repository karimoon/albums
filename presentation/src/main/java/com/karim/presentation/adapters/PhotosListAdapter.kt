package com.karim.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karim.presentation.R
import com.karim.presentation.model.PhotoAlbum
import kotlinx.android.synthetic.main.photo_list_item.view.*


class PhotosListAdapter(
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val photo = photosList.get(position)

        with(holder) {
            titleText.text = "Title: " + photo.title
            urlText.text = "Url: " + photo.url
            photoId.text = "Id: " + photo.id
            albumId.text= "albumId: " + photo.albumId

            //using Webview instead of Imageview because of the Okhttp error occured when using Glide.
            photoImage.loadUrl(photo.thumbnailUrl)

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


            //Unfortunatly Glide doesn't work for this kind of urls
            /*Glide.with(context)
                .load(photo.url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(photoImage)*/
        }



        holder.itemView.setOnClickListener {
            itemListener.onPhotoItemClick(photo)
        }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val titleText = itemView.title
        val photoImage = itemView.photoImage
        val photoId = itemView.photoId
        val albumId = itemView.albumId
        val urlText = itemView.url
    }


    interface PhotoItemListener {
        fun onPhotoItemClick(photo: PhotoAlbum)
    }

}

