package ar.edu.unicen.seminariomobile.ui.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ar.edu.unicen.seminariomobile.R
import ar.edu.unicen.seminariomobile.ui.components.Search
import ar.edu.unicen.seminariomobile.ui.components.LoadingScreen
import ar.edu.unicen.seminariomobile.ui.components.NotFound
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel



@Composable
fun MovieListScreen(
    movieViewModel: MovieViewModel,
    navController: NavController
) {


    // Observa/se Suscribe al StateFlow y obtén el estado actual, si el estado cambia movieList se va a enterar
    val movieList by movieViewModel.movies.collectAsStateWithLifecycle()
    val isLoading by movieViewModel.isLoading.collectAsStateWithLifecycle()
    val currentPage by movieViewModel.currentPage.collectAsStateWithLifecycle()
    val (search, setSearch) = remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        if (movieList.isEmpty())
            movieViewModel.getMovies(currentPage)
    }


    Search(
        searchText = search,
        setSearchText = setSearch,
        onSearchClick = {
            if (search.isNotBlank()) {
                movieViewModel.searchMovies(search)
            }
        }
    )


    if (isLoading) {
      LoadingScreen()
    }
    else if (movieList.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 80.dp,
                ),
            horizontalArrangement = Arrangement.Center
        ) {


            items(movieList) { movie ->
                MovieListItem(
                    movie = movie,
                    navController = navController
                )
            }


        //Boton para cargar mas peliculas
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                )
                {
                    TextButton(
                        onClick = {
                            movieViewModel.getNextPage()
                        },
                        Modifier.align(Alignment.Center),
                        colors = ButtonColors(
                            containerColor = colorResource(id = R.color.btnBackgroundColor),
                            contentColor = colorResource(id = R.color.btnContentColor),
                            disabledContentColor =  Color.Transparent,
                            disabledContainerColor = Color.Transparent
                        )
                    ) {
                        Text(stringResource(id = R.string.more_movies))
                    }
                }
            }
        }
    }
    else {
        NotFound()
    }
}