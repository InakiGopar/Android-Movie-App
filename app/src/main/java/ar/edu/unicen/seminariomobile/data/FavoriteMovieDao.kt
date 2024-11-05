package ar.edu.unicen.seminariomobile.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovie) : Long

    @Query("SELECT * FROM favorite_movies WHERE id = :movieId LIMIT 1")
    suspend fun getFavoriteMovieById(movieId: Long): FavoriteMovie?

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovie): Int

    @Query("SELECT * FROM favorite_movies")
    suspend fun getAllFavoriteMovies(): List<FavoriteMovie>

}