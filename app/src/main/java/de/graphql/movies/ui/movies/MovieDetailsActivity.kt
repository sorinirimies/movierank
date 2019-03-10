package de.graphql.movies.ui.movies

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import de.graphql.movies.R
import de.graphql.movies.injection.TMDB_BASE_IMAGE_URL
import de.graphql.movies.model.MovieItem
import de.graphql.movies.model.TmdbImgSize
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.androidx.viewmodel.ext.viewModel

const val PARAM_MOVIE = "de.graphql.movies.ui.movies.MOVIE"

class MoviesDetailsActivity : AppCompatActivity() {

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()
    private val castAdapter by lazy { CastAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val movie = intent.getParcelableExtra<MovieItem>(PARAM_MOVIE)

        movie.apply {
            //Load Poster image
            Picasso.get()
                .load("$TMDB_BASE_IMAGE_URL${TmdbImgSize.IMG_500.size}$poster_path")
                .into(imgDetailsPoster)

            tvDetailsTitle.text = title
            tvDetailsDescription.text = overview
            tvMovieDetailsAverage.text = "$vote_average"
            tvMovieDetailsReleaseDate.text = release_date
            tvMovieDetailsVoteCount.text = "$vote_count"
        }

        //Init movie cast list and set attributes
        rvCast.apply {
            adapter = castAdapter
            layoutManager = LinearLayoutManager(
                this@MoviesDetailsActivity,
                LinearLayout.HORIZONTAL,
                false
            )
        }

        //Register Live Data observers
        movieDetailsViewModel.movieCastLiveData.observe(this,
            Observer { cast -> castAdapter.addItems(cast) }
        )
        movieDetailsViewModel.errorMessageLiveData.observe(this,
            Observer { errorMessage -> Snackbar.make(contMovieDetails, errorMessage, Snackbar.LENGTH_SHORT).show() }
        )

        //Get cast based on movie ID
        movieDetailsViewModel.loadMovieCast(movie.id)
    }
}