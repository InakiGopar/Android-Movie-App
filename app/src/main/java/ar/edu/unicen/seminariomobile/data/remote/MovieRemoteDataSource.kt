package ar.edu.unicen.seminariomobile.data.remote

import ar.edu.unicen.seminariomobile.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Esta clase se encarga de obtener de los datos provenientes de fuentes externas,
 * en este caso de la API de peliculas
 * */
class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) {

    suspend fun getMovies(
        page: Int,

    ): List<Movie>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovies(ApiConfig.API_KEY, page)
                return@withContext response.body()?.results?.map { movie ->
                    movie.toMovie()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getMovieById(
        movieId: Long
    ): Movie? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovieById(movieId, ApiConfig.API_KEY)
                return@withContext response.body()?.toMovie()
            }
            catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun searchMovies(
        title : String,
        page: Int
    ): List<Movie>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.searchMovies(ApiConfig.API_KEY, title, page)
                return@withContext response.body()?.results?.map { movie ->
                    movie.toMovie()
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getTrendMovies(
        timeWindow: String
    ): List<Movie>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getTrendMovies(timeWindow, ApiConfig.API_KEY)
                return@withContext response.body()?.results?.map { trendMovie ->
                    trendMovie.toMovie()
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }


}