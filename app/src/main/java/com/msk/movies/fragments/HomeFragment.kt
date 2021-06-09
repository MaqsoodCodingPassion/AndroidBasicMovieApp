package com.msk.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msk.movies.*
import com.msk.movies.bookmark.BookMarkMovieAdapter
import com.msk.movies.model.SearchItem
import com.msk.movies.util.OnItemClickListener
import com.msk.movies.util.ViewModelFactory
import com.msk.movies.util.addOnItemClickListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    lateinit var navController: NavController

    private lateinit var mViewModel: MovieListViewModel
    private var mMovieListAdapter: MovieListAdapter? = null
    private lateinit var mSearchItemList: PagedList<SearchItem>
    private var mView: View? = null
    private lateinit var mBookMarkAdapter: BookMarkMovieAdapter
    private var searchMovie: String? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchMovie = arguments?.getString(SEARCH_MOVIE)
        if (mView == null) {
            mViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
            mView = inflater.inflate(R.layout.fragment_home, container, false)
            if (searchMovie != null) callSearchMovieAPI(searchMovie) else
                callSearchMovieAPI(DEFAULT_SEARCH_MOVIE_NAME)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initAdapter()
    }

    private fun initAdapter() {
        if (mMovieListAdapter == null) {
            mMovieListAdapter = MovieListAdapter()
            movieRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            movieRecyclerView.adapter = mMovieListAdapter

            mBookMarkAdapter = BookMarkMovieAdapter(listOf(), mViewModel) {
                val bundle = Bundle()
                bundle.putString(IMDB_ID_KEY, it.imdbID)
                navController.navigate(R.id.action_fragmentHome_to_movieDetails, bundle)
            }
            bookmarkedMovies.adapter = mBookMarkAdapter
            bookmarkedMovies.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
        }

        mViewModel.getBookMarkedMovies()
        mViewModel.bookMarkLiveData.observe(this, Observer {
            showOrHideBookmarks(it.isEmpty())
            it.let { mBookMarkAdapter.notifyDataSet(it) }
        })

        movieRecyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val bundle = Bundle()
                bundle.putString(POSTER_URL_KEY, mSearchItemList[position]?.poster)
                bundle.putString(IMDB_ID_KEY, mSearchItemList[position]?.imdbID)
                navController.navigate(R.id.action_fragmentHome_to_movieDetails, bundle)
            }
        })
    }

    private fun showOrHideBookmarks(show: Boolean) {
        if (show) {
            bookmarkedMovies.visibility = View.GONE
        } else {
            bookmarkedMovies.visibility = View.VISIBLE
        }
    }

    private fun callSearchMovieAPI(movieName: String?) {
        mViewModel.getMovieList(movieName!!, MOVIE_API_KEY).observe(this, Observer {
            if (it.size > 0) {
                mSearchItemList = it
                mMovieListAdapter?.submitList(it)
            } else {
                resetList()
                showErrorDialog(activity!!, resources.getString(R.string.movie_not_found))
            }
        })
    }

    private fun resetList() {
        hideKeyboard(activity!!)
        mMovieListAdapter?.submitList(null)
        mMovieListAdapter?.notifyDataSetChanged()
    }
}
