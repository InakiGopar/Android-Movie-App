package ar.edu.unicen.seminariomobile.viewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

    //Flow para peliculas en tendencia
    private val _trendMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendMovies: StateFlow<List<Movie>> = _trendMovies.asStateFlow()

    //Flow para seleccionar las tendencias del dia o de la semana
    private val _selectedTrend = MutableStateFlow("day")
    val selectedTrend: StateFlow<String> = _selectedTrend

    //Flow para una pelicula favorita en particular
    private val _favMovie = MutableStateFlow<Movie?>(null)
    val favMovie: StateFlow<Movie?> get() = _favMovie.asStateFlow()

    //flow para verificar si una pelicula esta en favoritos o no
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    //flow para verificar la conexion a internet
    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected

    // Control de búsqueda
    private val _currentSearchQuery = MutableStateFlow("")
    val currentSearchQuery: StateFlow<String> = _currentSearchQuery

    private var searchJob: Job? = null

    init {
        // Observa los cambios en _currentSearchQuery
        getMovies()
    }


    //Verifico si hay conexion a internet
    private fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Para versiones de Android 10 o superiores (API nivel 29+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            // Para versiones anteriores de Android
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    //Chequeo la conexion a internet
    fun checkConnectivity(context: Context ) {
        viewModelScope.launch {
            // Verificar la conectividad a internet
            _isConnected.value = isConnectedToInternet(context)
        }
    }


    //obtener todas las peliculas
    private fun getMovies(query: String = "") {

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

    //Obtener una pelicula favorita por su id
    fun getFavoriteMovieById(id: Long) {
        viewModelScope.launch {
            val favMovie = movieRepository.getFavoriteMovie(id)
            _favMovie.value = favMovie
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

    //traer las peliculas en tendencias
    fun getTrendMovies() {
        viewModelScope.launch {
            _isLoading.value = true

            val trendMovies = movieRepository.getTrendMovies(_selectedTrend.value)

            _trendMovies.value = trendMovies ?: emptyList()

            _isLoading.value = false
        }
    }

    //Cambiar el valor de dia o semana para las peliculas en tendencias
    fun setSelectedTrend(trend: String) {
        _selectedTrend.value = trend
    }

}