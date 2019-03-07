package de.graphql.movies.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import de.graphql.movies.R
import de.graphql.movies.utils.toMovieDetails
import kotlinx.android.synthetic.main.movies_fragment.*
import org.koin.androidx.viewmodel.ext.viewModel

class MoviesFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
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
                    putExtra(PARAM_MOVIE, it.toMovieDetails())
                })
            }
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }
        moviesViewModel.loadMovies(20)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesViewModel.moviesLiveData.observe(this,
            Observer { movieItemsState ->
                movieItemsState?.let { state ->
                    state.data?.let {
                        moviesAdapter.addItems(it)
                    }
                }
            }
        )

        moviesViewModel.errorMessage.observe(
            this,
            Observer { errorMsg ->
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
