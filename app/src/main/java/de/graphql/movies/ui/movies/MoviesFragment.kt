package de.graphql.movies.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import de.graphql.movies.R
import kotlinx.android.synthetic.main.movies_fragment.*
import org.koin.androidx.viewmodel.ext.viewModel

enum class MoviesSelectionType { MOVIES_ALL, MOVIES_ACTOR }

class MoviesFragment : Fragment() {
    private val moviesViewModel: MoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private var currentMoviesSelectionType = MoviesSelectionType.MOVIES_ALL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMovies.apply {
            moviesAdapter = MoviesAdapter {
                startActivity(Intent(activity, MoviesDetailsActivity::class.java).apply {
                    putExtra(PARAM_MOVIE, it)
                })
            }
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }
        moviesViewModel.loadMovies(40)
        swipeRefreshLayout.apply {
            setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                ContextCompat.getColor(requireContext(), R.color.pink)
            )
            setOnRefreshListener { getMoviesBasedOnSelection() }
        }
        fabMoviesAll.setOnClickListener {
            currentMoviesSelectionType = MoviesSelectionType.MOVIES_ALL
            getMoviesBasedOnSelection()
            fabMoviesMenu.collapse()
        }
        fabMoviesTitleDatePosterActor.setOnClickListener {
            currentMoviesSelectionType = MoviesSelectionType.MOVIES_ACTOR
            getMoviesBasedOnSelection()
            fabMoviesMenu.collapse()
        }
        fabMoviesTitleReleaseDatePosterCategoryVoteAverage.setOnClickListener { fabMoviesMenu.collapse() }
    }

    private fun getMoviesBasedOnSelection() = when (currentMoviesSelectionType) {
        MoviesSelectionType.MOVIES_ALL -> {
            moviesViewModel.loadMovies(50)
            progressLoader.visibility = View.VISIBLE
        }
        MoviesSelectionType.MOVIES_ACTOR -> {
            moviesViewModel.loadMoviesWithMainActor(2016)
            progressLoader.visibility = View.VISIBLE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesViewModel.moviesLiveData.observe(this,
            Observer { movieItems ->
                moviesAdapter.addItems(movieItems)
                swipeRefreshLayout.isRefreshing = false
                progressLoader.visibility = View.GONE
            }
        )
        moviesViewModel.errorMessageLiveData.observe(this,
            Observer { errorMsg ->
                swipeRefreshLayout.isRefreshing = false
                progressLoader.visibility = View.GONE
                Snackbar.make(
                    contMoviesFragment, errorMsg, Snackbar.LENGTH_SHORT
                ).show()
            }
        )
    }

    companion object {
        fun newInstance() = MoviesFragment()
        const val TAG_MOVIES = "movies-frag-tag"
    }
}
