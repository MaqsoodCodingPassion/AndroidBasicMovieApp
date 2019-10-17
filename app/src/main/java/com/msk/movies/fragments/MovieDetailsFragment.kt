package com.msk.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.msk.movies.MovieListViewModel
import com.msk.movies.MovieUtils
import com.msk.movies.R
import com.msk.movies.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_detail_body.*
import kotlinx.android.synthetic.main.layout_movie_detail_header.*
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var mView: View? = null

    private lateinit var mViewModel: MovieListViewModel

    private var movieId : String? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movieId = arguments?.getString("imdbID")
        if(mView==null){
            mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
            mView = inflater.inflate(R.layout.activity_movie_details, container, false)
            callSearchMovieAPI(movieId)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookmark.setOnClickListener {
            mViewModel.bookMarkMovie(movieId!!)
            bookmark.visibility = View.GONE
        }
    }

    private fun callSearchMovieAPI(imdbID: String?) {
        mViewModel.fetchMovieDetails(imdbID!!, "full", MovieUtils.MOVIE_API_KEY).observe(this, Observer {

            media_title.text = it.title
            release_date.text = "Imdb : "+it.imdbRating
            metascore.text = it.metascore
            casting.text = it.actors
            production.text = it.production
            type.text = it.runtime
            synopsis.text = it.plot
            if(it.bookmark){
                bookmark.visibility = View.GONE
            }else{
                bookmark.visibility = View.VISIBLE
            }
            val mPoster = it.poster
            if (mPoster!!.isNotEmpty() && mPoster != getString(R.string.na)) {
                Glide.with(context)
                    .load(mPoster)
                    .into(media_image)
            }
        })
    }
}
