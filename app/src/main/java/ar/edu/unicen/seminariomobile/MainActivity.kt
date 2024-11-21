package ar.edu.unicen.seminariomobile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.unicen.seminariomobile.ui.components.nav.AppNav
import ar.edu.unicen.seminariomobile.ui.movie.MovieDetailsScreen
import ar.edu.unicen.seminariomobile.ui.movies.favorites.FavoriteMoviesScreen
import ar.edu.unicen.seminariomobile.ui.movies.popular.MovieListScreen
import ar.edu.unicen.seminariomobile.ui.movies.tendencies.TendenciesMovieScreen
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val tabIndex = remember { mutableIntStateOf(0) } // 0 será "Películas Populares"
            val navController = rememberNavController()

            MaterialTheme {
                Scaffold(
                    containerColor = colorResource(id = R.color.backgroundColor),
                    bottomBar = {
                        AppNav(
                            tabIndex = tabIndex,
                            navController = navController,
                            movieViewModel = viewModel
                        )
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "movies",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("movies") {
                            MovieListScreen(
                                movieViewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable("favorites") {
                            FavoriteMoviesScreen(
                                movieViewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable("tendencies") {
                            TendenciesMovieScreen(
                                movieViewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable(
                            route = "movies/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getLong("id") ?: return@composable
                            MovieDetailsScreen(movieId = movieId, movieViewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}