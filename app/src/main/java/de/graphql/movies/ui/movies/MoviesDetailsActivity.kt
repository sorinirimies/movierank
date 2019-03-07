package de.graphql.movies.ui.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import de.graphql.movies.R
import de.graphql.movies.injection.TMDB_BASE_IMAGE_URL
import de.graphql.movies.model.MovieDetails
import de.graphql.movies.model.TmdbImgSize
import kotlinx.android.synthetic.main.activity_movie_details.*

const val PARAM_MOVIE = "de.graphql.movies.ui.movies.MOVIE"

class MoviesDetailsActivity : AppCompatActivity() {

    lateinit var viewModel: MoviesDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        viewModel = ViewModelProviders.of(this).get(MoviesDetailsViewModel::class.java)
        val movie = intent.getParcelableExtra<MovieDetails>(PARAM_MOVIE)
        Picasso.get().load("$TMDB_BASE_IMAGE_URL${TmdbImgSize.IMG_500.size}${movie.posterUrl}").into(imgDetailsPoster)
        tvDetailsTitle.text = movie.title
        tvDetailsDescription.text = movie.description
    }
}