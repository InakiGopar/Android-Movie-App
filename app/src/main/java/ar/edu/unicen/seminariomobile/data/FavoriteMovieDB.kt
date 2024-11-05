package ar.edu.unicen.seminariomobile.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ar.edu.unicen.seminariomobile.data.dto.GenreDb

@Database(
    entities = [
        FavoriteMovie::class,
        GenreDb::class
   ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FavoriteMovieDB: RoomDatabase() {
    abstract fun favoriteMovieDao() : FavoriteMovieDao
}