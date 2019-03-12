package de.graphql.movies.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sorinirimies.kotlinx.alertDialog
import com.sorinirimies.kotlinx.init
import de.graphql.movies.R
import kotlinx.android.synthetic.main.movies_fragment.*
import org.koin.androidx.viewmodel.ext.viewModel

enum class MoviesSelectionType { MOVIES_LOCAL_DB, MOVIES_BY_YEAR_WITH_ACTOR, MOVIES_BY_YEAR }

@Suppress("IMPLICIT_CAST_TO_ANY")
class MoviesFragment : Fragment() {
    private val moviesViewModel: MoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private var currentMoviesSelectionType = MoviesSelectionType.MOVIES_LOCAL_DB

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
        moviesViewModel.loadMoviesFromDb(40)
        swipeRefreshLayout.apply {
            setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                ContextCompat.getColor(requireContext(), R.color.pink)
            )
            setOnRefreshListener { getMoviesBasedOnSelection() }
        }
        fabMoviesFromLocalDb.setOnClickListener {
            currentMoviesSelectionType = MoviesSelectionType.MOVIES_LOCAL_DB
            getMoviesBasedOnSelection()
            fabMoviesMenu.collapse()
        }
        fabMoviesWithActor.setOnClickListener {
            currentMoviesSelectionType = MoviesSelectionType.MOVIES_BY_YEAR_WITH_ACTOR
            getMoviesBasedOnSelection()
            fabMoviesMenu.collapse()
        }
        fabMoviesByYear.setOnClickListener {
            currentMoviesSelectionType = MoviesSelectionType.MOVIES_BY_YEAR
            getMoviesBasedOnSelection()
            fabMoviesMenu.collapse()
        }
    }

    private fun getMoviesBasedOnSelection() = when (currentMoviesSelectionType) {
        MoviesSelectionType.MOVIES_LOCAL_DB -> {
            moviesViewModel.loadMoviesFromDb(50)
            contMoviesProgressBar.visibility = View.VISIBLE
        }
        MoviesSelectionType.MOVIES_BY_YEAR_WITH_ACTOR,
        MoviesSelectionType.MOVIES_BY_YEAR -> showSearchMoviesByYearDialog()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesViewModel.moviesLiveData.observe(this,
            Observer { movieItems ->
                moviesAdapter.addItems(movieItems)
                swipeRefreshLayout.isRefreshing = false
                contMoviesProgressBar.visibility = View.GONE
            }
        )
        moviesViewModel.errorMessageLiveData.observe(this,
            Observer { errorMsg ->
                swipeRefreshLayout.isRefreshing = false
                contMoviesProgressBar.visibility = View.GONE
                Snackbar.make(
                    contMoviesFragment, errorMsg, Snackbar.LENGTH_SHORT
                ).show()
            }
        )
    }

    private fun showSearchMoviesByYearDialog() = alertDialog(requireContext()) {
        val v = layoutInflater.init(R.layout.choose_year_dialog)
        setView(v)
        setPositiveButton(R.string.search_movies) { _, _ ->
            val yearResult = v.findViewById<EditText>(R.id.edtEnterYear).text.toString()
            if (currentMoviesSelectionType == MoviesSelectionType.MOVIES_BY_YEAR) {
                moviesViewModel.loadMoviesByYear(yearResult.toIntOrNull())
            } else if (currentMoviesSelectionType == MoviesSelectionType.MOVIES_BY_YEAR_WITH_ACTOR) {
                moviesViewModel.loadMoviesByYearWithMainActor(yearResult.toIntOrNull())
            }
            contMoviesProgressBar.visibility = View.VISIBLE
        }
        setNegativeButton(R.string.cancel) { _, _ -> }
    }

    companion object {
        fun newInstance() = MoviesFragment()
        const val TAG_MOVIES = "movies-frag-tag"
    }
}
