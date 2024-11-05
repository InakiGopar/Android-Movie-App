package ar.edu.unicen.seminariomobile.di

import ar.edu.unicen.seminariomobile.data.local.FavMovieLocalDataSource
import ar.edu.unicen.seminariomobile.data.remote.ApiConfig
import ar.edu.unicen.seminariomobile.data.remote.MovieApi
import ar.edu.unicen.seminariomobile.data.remote.MovieRemoteDataSource
import ar.edu.unicen.seminariomobile.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieDataSource: MovieRemoteDataSource,
        favMovieLocalDataSource: FavMovieLocalDataSource
    ): MovieRepository {
        return MovieRepository(movieDataSource, favMovieLocalDataSource)
    }



}