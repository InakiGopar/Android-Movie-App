package ar.edu.unicen.seminariomobile.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
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

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> get() = _movie.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Control de búsqueda
    private var currentSearchQuery: String = ""

    private var searchJob: Job? = null



    private fun getMovies(query: String = "") {

        currentSearchQuery = query

        searchJob?.cancel() // Cancelar el trabajo anterior si existe

        searchJob = viewModelScope.launch {
            // Obtener las peliculas paginadas
            movieRepository.getMovies(query)
                .collectLatest { pagingData ->
                    _movies.value = pagingData
                }
        }
    }

    fun getCurrentMovies() {
        getMovies(currentSearchQuery)
    }


    fun searchMovies(query: String) {
        getMovies(query) // Llama a getMovies con la consulta de búsqueda
    }


    fun getMovieById(id: Long) {
        viewModelScope.launch {

            _isLoading.value = true

            val movie = movieRepository.getMovieById(id)
            _movie.value = movie

            _isLoading.value = false

        }
    }





}