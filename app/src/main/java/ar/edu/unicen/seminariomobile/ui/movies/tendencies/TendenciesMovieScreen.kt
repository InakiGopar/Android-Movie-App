package ar.edu.unicen.seminariomobile.ui.movies.tendencies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ar.edu.unicen.seminariomobile.R
import ar.edu.unicen.seminariomobile.ui.components.LoadingScreen
import ar.edu.unicen.seminariomobile.ui.errors.NoConnection
import ar.edu.unicen.seminariomobile.ui.movies.popular.MovieListItem
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel


@Composable
fun TendenciesMovieScreen(
    movieViewModel: MovieViewModel,
    navController: NavController
) {

    val selectedTrend by movieViewModel.selectedTrend.collectAsStateWithLifecycle()
    val isLoading by movieViewModel.isLoading.collectAsStateWithLifecycle()
    val isConnected by movieViewModel.isConnected.collectAsStateWithLifecycle()

    // Obtener el contexto actual
    val context = LocalContext.current

    // Cargar las películas en tendencia según el tiempo seleccionado
    LaunchedEffect(selectedTrend) {
        movieViewModel.checkConnectivity(context) //chequear la conexion
        movieViewModel.getTrendMovies()
    }

    val trendMovies = movieViewModel.trendMovies.collectAsState()


    Surface(
        contentColor = colorResource(R.color.backgroundColor),
        color = colorResource(R.color.backgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón para películas en tendencia del día
                Button(
                    onClick = { movieViewModel.setSelectedTrend("day") },
                    colors = ButtonColors(
                        containerColor = if (selectedTrend == "day") colorResource(R.color.red_hover) else colorResource(R.color.brandColor),
                        contentColor = colorResource(id = R.color.white),
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    Text(text = stringResource(id = R.string.day_trend))
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón para películas en tendencia de la semana
                Button(
                    onClick = {  movieViewModel.setSelectedTrend("week") },
                    colors = ButtonColors(
                        containerColor = if (selectedTrend == "week") colorResource(R.color.red_hover) else colorResource(R.color.brandColor),
                        contentColor = colorResource(id = R.color.white),
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {
                    Text(text = stringResource(id = R.string.week_trend))
                }
            }


            if (!isConnected) {
                NoConnection()
            }
            else {

                if (isLoading) {
                    LoadingScreen()
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(trendMovies.value) { movie ->
                        MovieListItem(movie = movie, navController = navController)
                    }
                }

                if (trendMovies.value.isEmpty()) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.not_tendencies),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight(600),
                            color = colorResource(R.color.textPrimaryColor)
                        )
                    }
                }
            }
        }
    }
}