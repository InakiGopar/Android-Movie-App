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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.unicen.seminariomobile.R
import ar.edu.unicen.seminariomobile.data.dto.Genre
import ar.edu.unicen.seminariomobile.ui.components.genres.GenreList
import coil.compose.SubcomposeAsyncImage

@Composable
fun MovieDetails(
     title: String,
     overview: String,
     posterPath: String,
     voteAverage: Double,
     genres: List<Genre>


) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${posterPath}",
            contentDescription = "Poster of $title",
            modifier = Modifier.height(280.dp).width(180.dp),
            loading = {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.brandColor)
                )
            }
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(id = R.color.textPrimaryColor),
            fontWeight = FontWeight(800),
            textAlign = TextAlign.Center
        )

        Text(
            text = overview,
            modifier = Modifier.padding(18.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.textPrimaryColor),
            textAlign = TextAlign.Center
        )

        if (genres.isNotEmpty()) {
            GenreList(genres = genres.map { it.name })
        }


        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_star_rate_24),
                contentDescription = null,
                colorFilter = ColorFilter.tint(colorResource(id = R.color.yellow))
            )
            Text(
                text = voteAverage.toString(),
                modifier = Modifier.padding(top = 2.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(id = R.color.yellow),
                fontWeight = FontWeight(500)
            )
        }

    }

}