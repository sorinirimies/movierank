package de.graphql.movies.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.graphql.movies.MoviesQuery
import de.graphql.movies.R
import de.graphql.movies.injection.TMDB_BASE_IMAGE_URL
import de.graphql.movies.model.TmdbImgSize
import kotlinx.android.synthetic.main.listitem_movie.view.*

typealias  MovieSelected = (movieListItem: MoviesQuery.Movie) -> Unit

class MoviesAdapter(private val movieSelected: MovieSelected) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private val movieListItems: ArrayList<MoviesQuery.Movie> = ArrayList()

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

    fun addItem(item: MoviesQuery.Movie) {
        if (movieListItems.map { it.title() }.contains(item.title())) return
        movieListItems.add(item)
        notifyItemInserted(movieListItems.indexOf(item))
    }

    fun addItems(items: List<MoviesQuery.Movie>) {
        movieListItems.clear()
        movieListItems.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        movieListItems.clear()
        notifyDataSetChanged()
    }

    fun removeItem(item: MoviesQuery.Movie) {
        val position = movieListItems.indexOf(item)
        movieListItems.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return movieListItems.size
    }

    inner class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val contMovieItem = view.contMovieItem
        private val imgMovieTitle = view.imgItemMoviePoster
        private val tvMovieTitle = view.tvItemMovieTitle
        private val tvMovieScore = view.tvItemMovieRating
        private val tvMovieYear = view.tvItemMovieYear

        fun bind(movieListItem: MoviesQuery.Movie, movieSelected: MovieSelected) {
            Picasso.get()
                .load("$TMDB_BASE_IMAGE_URL${TmdbImgSize.IMG_185.size}${movieListItem.poster_path()}")
                .placeholder(R.drawable.ic_movies)
                .into(imgMovieTitle)
            tvMovieTitle.text = movieListItem.title()
            tvMovieScore.text = "${movieListItem.popularity()}"
            tvMovieYear.text = movieListItem.release_date()
            contMovieItem.setOnClickListener { movieSelected.invoke(movieListItem) }
        }
    }
}