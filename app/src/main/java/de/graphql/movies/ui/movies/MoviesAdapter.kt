package de.graphql.movies.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.graphql.movies.model.Movie
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.graphql.movies.R
import kotlinx.android.synthetic.main.listitem_movie.view.*

typealias  MovieSelected = (movie: Movie) -> Unit

class MoviesAdapter(private val movieSelected: MovieSelected) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private val movies: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listitem_movie,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) =
        holder.bind(movies[position], movieSelected)

    fun addItem(item: Movie) {
        if (movies.map { it.name }.contains(item.name)) return
        movies.add(item)
        notifyItemInserted(movies.indexOf(item))
    }

    fun addItems(items: List<Movie>) {
        movies.clear()
        movies.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        movies.clear()
        notifyDataSetChanged()
    }

    fun removeItem(item: Movie) {
        val position = movies.indexOf(item)
        movies.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val contMovieItem = view.contMovieItem
        private val imgMovieTitle = view.imgMovie
        private val tvMovieTitle = view.tvMovieTitle
        private val tvMovieScore = view.tvMovieScore
        private val tvMovieYear = view.tvMovieYear

        fun bind(movie: Movie, movieSelected: MovieSelected) {
            Picasso.get().load(movie.imgUrl).placeholder(R.drawable.ic_movies).into(imgMovieTitle)
            tvMovieTitle.text = movie.name
            tvMovieScore.text = "${movie.rank}"
            tvMovieYear.text = movie.year.toString()
            contMovieItem.setOnClickListener { movieSelected.invoke(movie) }
        }
    }
}