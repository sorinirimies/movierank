package de.graphql.movies.injection

import de.graphql.movies.ui.movies.MoviesDetailsViewModel
import de.graphql.movies.ui.movies.MoviesViewModel
import de.graphql.movies.ui.tvshows.TvShowsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MoviesViewModel() }
    viewModel { TvShowsViewModel() }
    viewModel { MoviesDetailsViewModel() }
}
