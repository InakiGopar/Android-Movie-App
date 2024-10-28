package ar.edu.unicen.seminariomobile.ui.movie

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unicen.seminariomobile.R
import ar.edu.unicen.seminariomobile.ui.components.LoadingScreen
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel


@Composable
fun MovieDetailsScreen(
    movieId: Long,
    movieViewModel: MovieViewModel
) {
    LaunchedEffect(movieId) {
        movieViewModel.getMovieById(movieId)
    }

    val movie by movieViewModel.selectedMovie.collectAsStateWithLifecycle()
    val isLoading by movieViewModel.isLoading.collectAsStateWithLifecycle()

    if (isLoading) {
       LoadingScreen()
    }
    else {
        if (movie != null) {
            MovieDetails(
                title = movie!!.title,
                overview = movie!!.overview,
                posterPath = movie!!.posterPath,
                voteAverage = movie!!.voteAverage,
                genres = movie!!.genres

            )
        } else {
            // Manejo del caso en que movie es null
            Text(
                text = stringResource(R.string.movie_not_found)
            )
        }
    }
}