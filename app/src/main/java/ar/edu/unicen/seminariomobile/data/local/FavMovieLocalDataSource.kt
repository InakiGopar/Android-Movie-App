package ar.edu.unicen.seminariomobile.data.local

import ar.edu.unicen.seminariomobile.data.FavoriteMovie.Companion.toFavoriteMovie
import ar.edu.unicen.seminariomobile.data.FavoriteMovieDao
import ar.edu.unicen.seminariomobile.data.Movie
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavMovieLocalDataSource @Inject constructor(
  private val favoriteMovieDao: FavoriteMovieDao
) {

    /**
     * Recupera todas las peliculas guardadas en la base de datos y mapea las mismas al objeto Movie
     * @return una lista de peliculas
     * */
    suspend fun getAll(): List<Movie>? {
       return withContext(Dispatchers.IO) {
           try {
               val favMovies = favoriteMovieDao.getAllFavoriteMovies()
               val movies = favMovies.map { it.toMovie() }
                return@withContext movies
           }
           catch (e: Exception) {
               e.printStackTrace()
               return@withContext null
           }
       }
    }

    /**
     * Inserta una pelicula dentro de la base de datos, luego de chequear si la pelicula a insertar
     * no existe.
     * @param movie La pelicula que va a ser insertada en la base de datos
     * @return el ID generado de la pelicula insertada, o null si ocurre un error o ya existe la misma
     * */
    suspend fun insert(
        movie: Movie
    ): Long? {
        return withContext(Dispatchers.IO) {
            try {
                //verificación si la pelicula ya existe
                val existingMovie = favoriteMovieDao.getFavoriteMovieById(movie.id)
                if (existingMovie != null) {
                    return@withContext null
                }

                val favMovie = movie.toFavoriteMovie()
                val generateId = favoriteMovieDao.insertFavoriteMovie(favMovie)
                return@withContext generateId
            }
            catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }

    }

    /**
     * Elimina una pelicula de la base de datos
     * @param movie la pelicula que se va a eliminar
     * @return verdadero si la operación fue exitosa, caso contrario retorna false
     * */
    suspend fun delete(
        movie: Movie
    ): Boolean {
       return withContext(Dispatchers.IO) {
           try {
                val favMovie = movie.toFavoriteMovie()
                val deleteRows = favoriteMovieDao.deleteFavoriteMovie(favMovie)
               return@withContext deleteRows > 0
           }
           catch (e: Exception) {
               e.printStackTrace()
               return@withContext false
           }
       }
    }

    /**
     * Verifica si una película es favorita.
     * @param movieId El ID de la película que se va a verificar
     * @return verdadero si la película es favorita, falso en caso contrario
     */
    suspend fun isFavorite(movieId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val favoriteMovie = favoriteMovieDao.getFavoriteMovieById(movieId)
                return@withContext favoriteMovie != null
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }
        }
    }

}