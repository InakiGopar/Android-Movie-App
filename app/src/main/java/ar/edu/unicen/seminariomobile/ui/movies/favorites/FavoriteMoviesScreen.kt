package ar.edu.unicen.seminariomobile.ui.movies.favorites

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel

@Composable
fun FavoriteMoviesScreen(
    movieViewModel: MovieViewModel,
    navController: NavController
) {
    Text("Peliculas Favoritas")
}