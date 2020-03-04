package com.karim.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.karim.presentation.R
import com.karim.presentation.adapters.PhotosListAdapter
import com.karim.presentation.model.PhotoAlbum
import com.karim.presentation.model.Status
import kotlinx.android.synthetic.main.albums_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class AlbumsFragment : Fragment() , PhotosListAdapter.PhotoItemListener {

    companion object {
        fun newInstance() = AlbumsFragment()
    }

    private val viewModel: com.karim.presentation.viewmodels.AlbumsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.albums_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.photoAlbumListSource.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.ERROR -> {
                    hideLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()
                    it.data?.let {photos ->

                        val photosListAdapter = PhotosListAdapter(photos,this
                        )
                        recyclerview.adapter= photosListAdapter

                        }
                    }
                }
            }
        )

    }

    override fun onPhotoItemClick(photo: PhotoAlbum) {

        Log.d("photo_clicked", photo.toString())
    }

    private fun showLoader() {
        progressBarLoader.visibility = View.VISIBLE
        recyclerview.alpha = 0.2f
    }

    private fun hideLoader() {
        progressBarLoader.visibility = View.GONE
        recyclerview.alpha = 1.0f
    }

}
