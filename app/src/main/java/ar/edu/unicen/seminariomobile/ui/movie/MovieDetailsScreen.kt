package ar.edu.unicen.seminariomobile.ui.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
        movieViewModel.getFavoriteMovieById(movieId)
    }

    val movie by movieViewModel.movie.collectAsStateWithLifecycle()
    val favMovie by movieViewModel.favMovie.collectAsStateWithLifecycle()
    val isLoading by movieViewModel.isLoading.collectAsStateWithLifecycle()

    if (isLoading) {
       LoadingScreen()
    }
    else {
        if (movie != null) {
            MovieDetails(
                movieViewModel = movieViewModel,
                movie = movie!!,
                title = movie!!.title,
                overview = movie!!.overview,
                posterPath = movie!!.posterPath,
                voteAverage = movie!!.voteAverage,
                genres = movie!!.genres

            )
        }
        else if (favMovie != null) {
            MovieDetails(
                movieViewModel = movieViewModel,
                movie = favMovie!!,
                title = favMovie!!.title,
                overview = favMovie!!.overview,
                posterPath = favMovie!!.posterPath,
                voteAverage = favMovie!!.voteAverage,
                genres = favMovie!!.genres

            )

        }
        else {
            // Manejo del caso en que no se encuentre ninguna pelicula
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.movie_not_found),
                    color = colorResource(id = R.color.textPrimaryColor),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

        }
    }
}