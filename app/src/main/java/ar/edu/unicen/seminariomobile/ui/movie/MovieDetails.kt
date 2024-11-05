package ar.edu.unicen.seminariomobile.ui.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unicen.seminariomobile.R
import ar.edu.unicen.seminariomobile.data.Movie
import ar.edu.unicen.seminariomobile.data.dto.Genre
import ar.edu.unicen.seminariomobile.ui.components.genres.GenreList
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel
import coil.compose.SubcomposeAsyncImage

@Composable
fun MovieDetails (
    movieViewModel: MovieViewModel,
    movie: Movie,
    title: String,
    overview: String,
    posterPath: String?,
    voteAverage: Double,
    genres: List<Genre>
) {

    val isFavorite by movieViewModel.isFavorite.collectAsStateWithLifecycle()

    // Verificamos si la película está en favoritos cuando se abre esta pantalla
    LaunchedEffect(movie.id) {
        movieViewModel.checkIfFavorite(movie.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        //Poster de la pelicula
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${posterPath}",
            contentDescription = "Poster of $title",
            modifier = Modifier.height(280.dp).width(180.dp),
            loading = {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.brandColor)
                )
            },
            error = {
                Image(
                    painter = painterResource(id = R.drawable.no),
                    contentDescription = "Imagen predeterminada",
                    modifier = Modifier.size(180.dp),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.semidark_grey))
                )
            }
        )

        //Titulo de la pelicula

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(id = R.color.textPrimaryColor),
            fontWeight = FontWeight(800),
            textAlign = TextAlign.Center
        )

        //Boton para agregar una peli a favoritos

        Button(
            onClick = { movieViewModel.toggleFavoriteMovie(movie) },
            modifier = Modifier.padding(vertical = 16.dp)
                .clip(CircleShape), // Botón sea circular,

            colors = ButtonColors (
                containerColor = colorResource(id = R.color.brandColor),
                contentColor = colorResource(id = R.color.btnBackgroundColor),
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            Image (
                painter = painterResource(
                    id = if (isFavorite) R.drawable.ic_remove_favorite else R.drawable.ic_add_favorite
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(55.dp)
                    .width(55.dp),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.white))
            )
        }

        //Descripción de la pelicula

        Text(
            text = overview,
            modifier = Modifier.padding(18.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.textPrimaryColor),
            textAlign = TextAlign.Center
        )

        //Lista de generos de la pelicula

        if (genres.isNotEmpty()) {
            GenreList(genres = genres.map { it.name })
        }


        //Calificación de la pelicula

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_star_rate_24),
                contentDescription = null,
                modifier = Modifier.padding(bottom = 15.dp),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.yellow))
            )
            Text(
                text = voteAverage.toString(),
                modifier = Modifier.padding(top = 2.dp, bottom = 15.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(id = R.color.yellow),
                fontWeight = FontWeight(500)
            )
        }



    }

}