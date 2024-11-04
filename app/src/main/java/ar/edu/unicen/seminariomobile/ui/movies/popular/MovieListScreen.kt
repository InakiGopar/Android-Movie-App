package ar.edu.unicen.seminariomobile.ui.movies.popular

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ar.edu.unicen.seminariomobile.data.Movie
import ar.edu.unicen.seminariomobile.ui.components.Search
import ar.edu.unicen.seminariomobile.ui.components.LoadingScreen
import ar.edu.unicen.seminariomobile.ui.components.NotFound
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel



@Composable
fun MovieListScreen(
    movieViewModel: MovieViewModel,
    navController: NavController
) {


    val movies: LazyPagingItems<Movie> = movieViewModel.movies.collectAsLazyPagingItems()
    val (search, setSearch) = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        movieViewModel.getCurrentMovies()
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

    //Mensaje que muestra que no se encontraron peliculas
    if ( movies.itemCount == 0 && movies.loadState.append.endOfPaginationReached) {
        NotFound()
    }

    when (movies.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingScreen()
        }


        is LoadState.Error -> {
            NotFound()
        }


        else -> {
            //recordar el estado de la lista de peliculas
            val movieListState = rememberLazyGridState()

            LazyVerticalGrid(
                state = movieListState,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                items(movies.itemCount) { index ->
                    val movie = movies[index]
                    if (movie != null) {
                        MovieListItem(
                            movie = movie,
                            navController = navController
                        )
                    }
                }
            }
        }
    }

}