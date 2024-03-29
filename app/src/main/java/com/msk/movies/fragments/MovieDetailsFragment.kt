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
import com.msk.movies.*
import com.msk.movies.model.MediaEntity
import com.msk.movies.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_detail_body.*
import kotlinx.android.synthetic.main.layout_movie_detail_header.*
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var mView: View? = null

    private lateinit var mViewModel: MovieListViewModel

    private lateinit var movieId: String

    private lateinit var mediaEntity: MediaEntity

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieId = arguments?.getString(IMDB_ID_KEY).toString()
        if (mView == null) {
            mViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
            mView = inflater.inflate(R.layout.fragment_movie_details, container, false)
            mViewModel.fetchMovieDetails(movieId, "full", MOVIE_API_KEY)
            callSearchMovieAPI()
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entityLiveData = mViewModel.getEntity(movieId)
        entityLiveData.observe(this, Observer {
            try {
                if (it.imdbID.equals(movieId)) {
                    bookmark.visibility = View.GONE
                }
            } //If DB does not have record then it throw NullPointerException
            catch (exp: NullPointerException) {
            }
        })

        bookmark.setOnClickListener {
            mViewModel.saveMovieDetailsRecord(mediaEntity)
            //mViewModel.bookMarkMovie(movieId!!)
            bookmark.visibility = View.GONE
        }
    }

    private fun callSearchMovieAPI() {
        mViewModel.moviesListResponse
            .observe(this, Observer {
                mediaEntity = it
                media_title.text = it.title
                release_date.text = IMDB_STR + it.imdbRating
                metascore.text = it.metascore
                casting.text = it.actors
                production.text = it.production
                type.text = it.runtime
                synopsis.text = it.plot
                val mPoster = it.poster
                if (mPoster!!.isNotEmpty() && mPoster != getString(R.string.na)) {
                    Glide.with(context)
                        .load(mPoster)
                        .into(media_image)
                }
            })
    }
}
