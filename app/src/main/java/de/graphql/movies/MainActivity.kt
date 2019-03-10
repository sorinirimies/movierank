package de.graphql.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import de.graphql.movies.ui.movies.MoviesFragment
import de.graphql.movies.ui.tvshows.TvShowsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.transaction {
            add(
                R.id.frag_container, MoviesFragment.newInstance(),
                MoviesFragment.TAG_MOVIES
            )
        }

        //FIXME re-enable when implementing the TV Show part
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_movies -> supportFragmentManager.transaction {
                    replace(
                        R.id.frag_container,
                        MoviesFragment.newInstance(),
                        MoviesFragment.TAG_MOVIES
                    )
                }
                R.id.action_tv_shows -> {
                    supportFragmentManager.transaction {
                        replace(
                            R.id.frag_container,
                            TvShowsFragment.newInstance(),
                            TvShowsFragment.TAG_TV_SHOWS
                        )
                    }
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
