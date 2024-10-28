package ar.edu.unicen.seminariomobile.data.repository

import ar.edu.unicen.seminariomobile.data.Movie
import ar.edu.unicen.seminariomobile.data.remote.MovieRemoteDataSource
import javax.inject.Inject


/**
 * Esta clase es la intermediaria entre el ViewModel y los datos
 * El objetivo principal de un repositorio es abstraer el acceso a los datos,
 * ya sea que provengan de una base de datos, una API externa, archivos locales,
 * o cualquier otra fuente.
 *
 * */
class MovieRepository @Inject constructor(
    private val movieDataSource: MovieRemoteDataSource
) {

    /**
     * @return este metodo devuelve la lista de peliculas populares provenientes de la clase
     * MovieRemoteDataSource.
     *
     * @param apiKey es la llave de seguridad que necesitamos para tener acceso a la API
     * @param page aca va el numero de paginado
     * */
    suspend fun getMovies( page: Int): List<Movie>? {
        return movieDataSource.getMovies(page)
    }

    suspend fun getMovieById(movieId: Long): Movie? {
        return movieDataSource.getMovieById(movieId)
    }

    suspend fun searchMovies(title: String): List<Movie>? {
        return movieDataSource.searchMovies(title)
    }


}