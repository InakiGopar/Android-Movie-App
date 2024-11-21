package ar.edu.unicen.seminariomobile.ui.movies.popular

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.unicen.seminariomobile.R
import ar.edu.unicen.seminariomobile.data.Movie
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.delay

@Composable
fun MovieListItem(
    movie: Movie,
    navController: NavController
) {

    // Estado para prevenir clics rápidos y evitar errores de duplicación
    var isClickEnabled by remember { mutableStateOf(true) }

    // Manejar el clic
    val onMovieClick = rememberUpdatedState {
        if (isClickEnabled) {
            // Deshabilitar el clic
            isClickEnabled = false

            // Navegar a la pantalla de detalles de la película
            navController.navigate("movies/${movie.id}")

        }
    }

    // Volver a habilitar el clic después de 500ms
    LaunchedEffect(Unit) {
        delay(500)
        isClickEnabled = true
    }

    Column (
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onMovieClick.value),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = "Poster of ${movie.title}",
            modifier = Modifier
                .height(180.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(id = R.color.textPrimaryColor),
            textAlign = TextAlign.Center
        )
    }
}