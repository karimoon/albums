package com.karim.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.karim.presentation.R
import com.karim.presentation.adapters.PhotosListAdapter
import com.karim.presentation.model.PhotoAlbum
import com.karim.presentation.model.Status
import com.karim.presentation.sharedUiRepos.SharedUIRepo
import com.karim.presentation.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.albums_fragment.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel


class AlbumsFragment : Fragment() , PhotosListAdapter.PhotoItemListener {

    companion object {
        fun newInstance() = AlbumsFragment()
    }

    private val viewModel: com.karim.presentation.viewmodels.AlbumsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.albums_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.photoAlbumListSource.observe(viewLifecycleOwner, Observer {
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
        get<SharedUIRepo>().selectedPhoto = photo

        startActivity(Intent(requireActivity(), DetailActivity::class.java))
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
