package de.graphql.movies.ui.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import de.graphql.movies.R
import org.koin.android.viewmodel.ext.viewModel

class TvShowsFragment : Fragment() {

    private val viewModel by viewModel<TvShowsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.series_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance() = TvShowsFragment()
        const val TAG_TV_SHOWS = "tag-tv-show"

    }
}
