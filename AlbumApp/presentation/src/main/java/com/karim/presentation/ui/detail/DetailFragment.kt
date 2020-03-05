package com.karim.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karim.presentation.R
import com.karim.presentation.sharedUiRepos.SharedUIRepo
import com.karim.presentation.viewmodels.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(viewModel.selectedPhoto == null) {

            viewModel.selectedPhoto = get<SharedUIRepo>().selectedPhoto
        }

        initViews()
    }

    private fun initViews() {
        val photo = viewModel.selectedPhoto

        photo?.let {
            albumId.text = "AlbumId: "+ it.albumId
            photoId.text = "Id: "+ it.id
            title.text = "Title: "+ it.title
            url.text = "Url: "+ it.url
            thumbnailUrl.text = "thumbnailUrl: "+ it.thumbnailUrl
            photoImage.loadUrl(it.thumbnailUrl)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        get<SharedUIRepo>().selectedPhoto = null
    }

}
