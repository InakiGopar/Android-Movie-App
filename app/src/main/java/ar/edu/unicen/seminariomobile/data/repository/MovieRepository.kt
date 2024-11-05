package ar.edu.unicen.seminariomobile.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ar.edu.unicen.seminariomobile.data.Movie
import ar.edu.unicen.seminariomobile.data.local.FavMovieLocalDataSource
import ar.edu.unicen.seminariomobile.data.remote.MoviePagingSource
import ar.edu.unicen.seminariomobile.data.remote.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Esta clase es la intermediaria entre el ViewModel y los datos.
 * El objetivo principal de un repositorio es abstraer el acceso a los datos,
 * ya sea que provengan de una base de datos, una API externa, archivos locales,
 * o cualquier otra fuente.
 *
 * */
class MovieRepository @Inject constructor(
    private val movieDataSource: MovieRemoteDataSource,
    private val favMovieLocalDataSource: FavMovieLocalDataSource
) {

    fun getMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviePagingSource(movieDataSource, query) }
        ).flow
    }


    suspend fun getMovieById(movieId: Long): Movie? {
        return movieDataSource.getMovieById(movieId)
    }

    suspend fun getFavoritesMovies() : List<Movie>? {
        return favMovieLocalDataSource.getAll()
    }

    suspend fun addFavoriteMovie(movie: Movie): Long? {
        return favMovieLocalDataSource.insert(movie)
    }

    suspend fun removeFavoriteMovie(movie: Movie): Boolean {
        return favMovieLocalDataSource.delete(movie)
    }

    suspend fun isFavorite(movieId: Long): Boolean {
        return favMovieLocalDataSource.isFavorite(movieId)
    }




}