package com.msk.movies


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khan.movieskotlin.MovieListAdapter
import com.khan.movieskotlin.MovieListViewModel
import com.khan.movieskotlin.MovieUtils
import com.khan.movieskotlin.model.SearchItem
import com.msk.movies.util.OnItemClickListener
import com.msk.movies.util.ViewModelFactory
import com.msk.movies.util.addOnItemClickListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    lateinit var navController: NavController

    private lateinit var viewModel: MovieListViewModel
    private var mMovieListAdapter: MovieListAdapter? = null
    private var mSearchItemList : PagedList<SearchItem>? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
        initView()
        initAdapter()
        callSearchMovieAPI(MovieUtils.DEFAULT_SEARCH_MOVIE_NAME)
    }

    private fun initAdapter() {
        mMovieListAdapter = MovieListAdapter()
        movieRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        movieRecyclerView.adapter = mMovieListAdapter

        movieRecyclerView.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                var bundle = bundleOf("POSTER_URL" to mSearchItemList?.get(position)?.poster)
                navController!!.navigate(R.id.action_fragmentHome_to_movieDetails,bundle)
            }
        })
    }

    private fun callSearchMovieAPI(movieName: String?) {
        viewModel.getMovieList(movieName!!, MovieUtils.MOVIE_API_KEY).observe(this, Observer {
            if(it.size > 0){
                mSearchItemList = it
                mMovieListAdapter!!.submitList(it)
            }else{
                resetList()
                MovieUtils.showErrorDialog(activity!!, resources.getString(R.string.movie_not_found))
            }
        })
    }

    private fun initView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                MovieUtils.hideKeyboard(activity!!)
                resetList()
                callSearchMovieAPI(query
                )
                return false
            }
        })
    }

    private fun resetList() {
        MovieUtils.hideKeyboard(activity!!)
        mMovieListAdapter!!.submitList(null)
        mMovieListAdapter!!.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRetainInstance(true);
        navController = Navigation.findNavController(view)
    }
}
