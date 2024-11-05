package ar.edu.unicen.seminariomobile.di

import android.content.Context
import androidx.room.Room
import ar.edu.unicen.seminariomobile.data.FavoriteMovieDB
import ar.edu.unicen.seminariomobile.data.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideFavoriteMoviesDb(
        @ApplicationContext context: Context
    ): FavoriteMovieDB {
        return Room.databaseBuilder(
            context = context,
            klass = FavoriteMovieDB::class.java,
            name = "favorite_movies_db"
        ).build()
    }

    @Provides
    fun provideFavoriteMovieDao(
        database: FavoriteMovieDB
    ): FavoriteMovieDao {
        return database.favoriteMovieDao()
    }
}