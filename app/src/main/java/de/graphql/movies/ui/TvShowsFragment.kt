package de.graphql.movies.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import de.graphql.movies.R

class TvShowsFragment : Fragment() {

    companion object {
        fun newInstance() = TvShowsFragment()
        const val TAG_TV_SHOWS = "tag-tv-show"
    }

    private lateinit var viewModel: TvShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.series_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TvShowsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
