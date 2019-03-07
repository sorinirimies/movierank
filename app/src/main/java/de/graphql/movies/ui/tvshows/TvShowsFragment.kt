package de.graphql.movies.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.graphql.movies.R
import org.koin.androidx.viewmodel.ext.viewModel

class TvShowsFragment : Fragment() {

    private val tvShowsviewModel: TvShowsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_shows_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance() = TvShowsFragment()
        const val TAG_TV_SHOWS = "tag-tv-show"

    }
}
