package de.graphql.movies.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.graphql.movies.R
import de.graphql.movies.injection.TMDB_BASE_IMAGE_URL
import de.graphql.movies.model.MovieItem
import de.graphql.movies.model.TmdbImgSize
import kotlinx.android.synthetic.main.listitem_movie.view.*


private typealias  MovieSelected = (movieListItem: MovieItem) -> Unit

class MoviesAdapter(private val movieSelected: MovieSelected) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val movieListItems: ArrayList<MovieItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listitem_movie,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) =
        holder.bind(movieListItems[position], movieSelected)

    fun addItem(item: MovieItem) {
        if (movieListItems.map { it.title }.contains(item.title)) return
        movieListItems.add(item)
        notifyItemInserted(movieListItems.indexOf(item))
    }

    fun addItems(items: List<MovieItem>) {
        movieListItems.clear()
        movieListItems.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        movieListItems.clear()
        notifyDataSetChanged()
    }

    fun removeItem(item: MovieItem) {
        val position = movieListItems.indexOf(item)
        movieListItems.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = movieListItems.size

    inner class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val contMovieItem = view.contMovieItem
        private val imgMovieTitle = view.imgItemMoviePoster
        private val tvMovieTitle = view.tvItemMovieTitle
        private val tvItemMovieVoteCount = view.tvItemMovieVoteCount
        private val tvMovieVoteAverage = view.tvItemMovieVoteAverage
        private val tvMovieActorName = view.tvCastName
        private val tvMovieYear = view.tvItemMovieYear
        private val tvMoviePopularity = view.tvItemMoviePopularity
        private val imgMovieMainActor = view.imgMovieCast

        fun bind(movieListItem: MovieItem, movieSelected: MovieSelected) {
            Picasso.get()
                .load("$TMDB_BASE_IMAGE_URL${TmdbImgSize.IMG_342.size}${movieListItem.poster_path}")
                .placeholder(R.drawable.ic_movies)
                .into(imgMovieTitle)
            imgMovieMainActor.visibility = View.GONE
            movieListItem.profile_path?.let {
                imgMovieMainActor.visibility = View.VISIBLE
                Picasso.get()
                    .load("$TMDB_BASE_IMAGE_URL${TmdbImgSize.IMG_ORIGINAL.size}$it")
                    .into(imgMovieMainActor)
            }
            tvMovieActorName.text = movieListItem.name
            tvMovieTitle.text = movieListItem.title
            tvItemMovieVoteCount.text = "${movieListItem.vote_count}"
            tvMovieYear.text = movieListItem.release_date
            tvMoviePopularity.text = "${movieListItem.popularity}"
            tvMovieVoteAverage.text = "${movieListItem.vote_average}"
            contMovieItem.setOnClickListener { movieSelected.invoke(movieListItem) }
        }
    }
}