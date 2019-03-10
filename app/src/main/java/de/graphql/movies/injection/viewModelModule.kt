package de.graphql.movies.injection

import de.graphql.movies.ui.movies.MovieDetailsViewModel
import de.graphql.movies.ui.movies.MoviesViewModel
import de.graphql.movies.ui.tvshows.TvShowsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { TvShowsViewModel() }
}
