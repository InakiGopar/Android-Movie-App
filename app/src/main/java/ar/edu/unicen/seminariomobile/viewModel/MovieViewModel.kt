package ar.edu.unicen.seminariomobile.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminariomobile.data.Movie
import ar.edu.unicen.seminariomobile.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> get() = _movies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> get() = _selectedMovie.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> get() = _currentPage

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun getMovies(page: Int) {
        viewModelScope.launch {

            _isLoading.value = true

            //obtengo las peliculas del Repository, pasandole la llave y el número de pagina
            val movieList = movieRepository.getMovies(page)
            _movies.value = movieList ?: emptyList()

            _isLoading.value = false
        }
    }

    fun getMovieById(id: Long) {
        viewModelScope.launch {

            _isLoading.value = true

            val movie = movieRepository.getMovieById(id)
            _selectedMovie.value = movie

            _isLoading.value = false

        }
    }

    fun getNextPage() {
        viewModelScope.launch {
            _isLoading.value = true

            val nextPage = currentPage.value + 1
            val newMovies = movieRepository.getMovies(nextPage)

            newMovies?.let {
                _movies.value += it  // Agrega las nuevas películas a la lista actual
                _currentPage.value = nextPage
            }

            _isLoading.value = false
        }
    }

    fun searchMovies(title: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val searchResults = movieRepository.searchMovies(title)
            _movies.value = searchResults ?: emptyList()

            _isLoading.value = false
        }
    }




}