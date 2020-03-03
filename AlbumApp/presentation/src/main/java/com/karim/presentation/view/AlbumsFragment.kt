package com.karim.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.karim.presentation.R
import com.karim.presentation.model.Status
import org.koin.android.viewmodel.ext.android.viewModel


class AlbumsFragment : Fragment() {

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
            Log.d("viewmodels_res",it.toString())
            when (it.status) {
                Status.LOADING -> {
                    println("Photos Loading")
                    Log.d("viewmodels","Photos Loading")
                    //showLoader()
                }
                Status.ERROR -> {
                    Log.d("viewmodels","Photos ERROR:")
                    println("Transactions ERROR: ${it.message}")

                    //hideLoader()
                }
                Status.SUCCESS -> {
                    //hideLoader()
                    Log.d("viewmodels", "sucsess")
                    println("Transactions data: ${it.data}")
                    it.data?.let {transactions ->
                        Log.d("viewmodels", transactions.toString())

                        }
                    }
                }
            }
        )

    }

}
