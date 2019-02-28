package de.graphql.movies.ui.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.viewModel
import de.graphql.movies.R

const val PARAM_MOVIE= "de.graphql.movies.ui.movies.MOVIE"
class MoviesDetailsActivity : AppCompatActivity() {

    private val viewModel by viewModel<MoviesDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_details)
    }
}