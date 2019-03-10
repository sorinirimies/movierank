package de.graphql.movies.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.graphql.movies.R
import de.graphql.movies.injection.TMDB_BASE_IMAGE_URL
import de.graphql.movies.model.CastItem
import de.graphql.movies.model.TmdbImgSize
import kotlinx.android.synthetic.main.listitem_cast.view.*

class CastAdapter : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    private val castList = arrayListOf<CastItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CastViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.listitem_cast,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) = holder.bind(castList[position])

    override fun getItemCount() = castList.size

    fun addItems(items: List<CastItem>) {
        castList.clear()
        castList.addAll(items)
        notifyDataSetChanged()
    }

    inner class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgMovieCast = view.imgMovieCast
        private val tvCastName = view.tvCastName
        private val tvCastCharacter = view.tvCastCharacter

        fun bind(castItem: CastItem) {
            tvCastName.text = castItem.name
            tvCastCharacter.text = castItem.character
            castItem.profile_path?.let {
                imgMovieCast.visibility = View.VISIBLE
                Picasso.get()
                    .load("$TMDB_BASE_IMAGE_URL${TmdbImgSize.IMG_185.size}$it")
                    .into(imgMovieCast)
            }
        }
    }
}