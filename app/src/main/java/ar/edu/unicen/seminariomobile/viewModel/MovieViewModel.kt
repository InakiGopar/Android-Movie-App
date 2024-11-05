package ar.edu.unicen.seminariomobile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ar.edu.unicen.seminariomobile.data.Movie
import ar.edu.unicen.seminariomobile.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Esta clase es la intermediaria entre los datos y la vista, se encarga de obtener los datos
 * y pasarsela a la ui
 * **/
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    // Flow para las películas paginadas
    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> = _movies.asStateFlow()

    //Flow para una pelicula en particular
    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> get() = _movie.asStateFlow()

    //flow para el estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    //flow para las peliculas favoritas
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    //flow para verificar si una pelicula esta en favoritos o no
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()


    // Control de búsqueda
    private var currentSearchQuery: String = ""

    private var searchJob: Job? = null


    //obtener todas las peliculas
    private fun getMovies(query: String = "") {

        currentSearchQuery = query

        searchJob?.cancel() // Cancelar el trabajo anterior si existe

        searchJob = viewModelScope.launch {
            // Obtener las peliculas paginadas
            movieRepository.getMovies(query)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _movies.value = pagingData
                }
        }
    }

    //traerme las peliculas actuales
    fun getCurrentMovies() {
        getMovies(currentSearchQuery)
    }

    //Buscar peliculas en base a un titulo
    fun searchMovies(query: String) {
        getMovies(query) // Llama a getMovies con la consulta de búsqueda
    }

    //Obtener una pelicula por su id
    fun getMovieById(id: Long) {
        viewModelScope.launch {

            _isLoading.value = true

            val movie = movieRepository.getMovieById(id)
            _movie.value = movie

            _isLoading.value = false

        }
    }

    // Obtener las películas favoritas
    fun loadFavoriteMovies() {
        viewModelScope.launch {
            val favorites = movieRepository.getFavoritesMovies()
            _favoriteMovies.value = favorites ?: emptyList()
        }
    }

    // Agregar o eliminar una película de favoritos
    fun toggleFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                // Si la película ya es favorita, la eliminamos
                movieRepository.removeFavoriteMovie(movie)
            } else {
                // Si no es favorita, la agregamos
                movieRepository.addFavoriteMovie(movie)
            }
            _isFavorite.value = !_isFavorite.value
            loadFavoriteMovies() // Recarga la lista de favoritos actualizada
        }
    }
    fun checkIfFavorite(movieId: Long) {
        viewModelScope.launch {
            _isFavorite.value = movieRepository.isFavorite(movieId)
        }
    }

}