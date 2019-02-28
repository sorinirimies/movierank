package de.graphql.movies.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.graphql.movies.R
import kotlinx.android.synthetic.main.movies_fragment.*
import org.koin.android.viewmodel.ext.viewModel
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MoviesFragment : Fragment() {

    private val viewModel by viewModel<MoviesViewModel>()
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
                    putExtra(PARAM_MOVIE, it)
                })
            }
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    companion object {
        fun newInstance() = MoviesFragment()
        const val TAG_MOVIES = "movies-frag-tag"
    }
}
