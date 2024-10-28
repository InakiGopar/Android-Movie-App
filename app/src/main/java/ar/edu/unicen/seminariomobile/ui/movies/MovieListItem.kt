package ar.edu.unicen.seminariomobile.ui.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.unicen.seminariomobile.R
import ar.edu.unicen.seminariomobile.data.Movie
import coil.compose.SubcomposeAsyncImage

@Composable
fun MovieListItem(
    movie: Movie,
    navController: NavController
) {

    Column (
        modifier = Modifier.padding(16.dp).clickable {
            navController.navigate("movies/${movie.id}")
        },
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