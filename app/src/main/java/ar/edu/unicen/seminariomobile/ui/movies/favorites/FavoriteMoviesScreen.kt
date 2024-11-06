package ar.edu.unicen.seminariomobile.ui.movies.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.unicen.seminariomobile.R
import ar.edu.unicen.seminariomobile.ui.movies.popular.MovieListItem
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel

@Composable
fun FavoriteMoviesScreen(
    movieViewModel: MovieViewModel,
    navController: NavController
) {
    // Observa el estado de las películas favoritas
    val favoriteMovies = movieViewModel.favoriteMovies.collectAsState()

    // Cargar las películas favoritas cuando la pantalla se compone
    LaunchedEffect(Unit) {
        movieViewModel.loadFavoriteMovies()
    }

    Surface (
        contentColor = colorResource(R.color.backgroundColor),
        color = colorResource(R.color.backgroundColor)
    ) {

        //mensaje que no hay peliculas favoritas
        if (favoriteMovies.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.not_favorites_movies),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight(600),
                    color = colorResource(R.color.textPrimaryColor)
                    )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize().padding(top = 25.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                //texto de peliculas favoritas
                Text(
                    text = stringResource(id = R.string.favorites_movies),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight(600),
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.textPrimaryColor)
                )
            }
            //peliculas favoritas
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                items(favoriteMovies.value) { movie ->
                    MovieListItem(movie = movie, navController = navController)
                }
            }
        }
    }

}