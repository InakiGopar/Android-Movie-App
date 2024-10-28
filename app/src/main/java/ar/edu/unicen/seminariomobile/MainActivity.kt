package ar.edu.unicen.seminariomobile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.unicen.seminariomobile.ui.MovieDetailsScreen
import ar.edu.unicen.seminariomobile.ui.MovieListScreen
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MovieViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                Surface(
                    color = colorResource(id = R.color.backgroundColor)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "movies"
                    ) {

                        composable(
                            route = "movies"
                        ) {
                            // Llama a la lista de películas
                            MovieListScreen(
                                movieViewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable(
                            route = "movies/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.LongType })
                        ) {
                            backStackEntry ->
                            // Aquí extraemos el 'id' que se pasa en la ruta
                            val movieId = backStackEntry.arguments?.getLong("id") ?: return@composable

                            // Usamos ese 'id' para mostrar la pantalla de detalles
                            MovieDetailsScreen(movieId = movieId, movieViewModel = viewModel)
                        }

                    }
                }
            }
        }
    }
}