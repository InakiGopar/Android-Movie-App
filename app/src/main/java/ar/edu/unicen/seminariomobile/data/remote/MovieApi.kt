package ar.edu.unicen.seminariomobile.data.remote

import ar.edu.unicen.seminariomobile.data.dto.MovieData
import ar.edu.unicen.seminariomobile.data.dto.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    /**
     * @query api_key: pasar clave de API para autenticar las solicitudes.
     * @query page: Especifica la página de resultados que deseas obtener.
     * */
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieDto>


    /**
     * @path movie_id: el id pasado por parametro que cambiara dinamicamente
     * @query api_key: pasar clave de API para autenticar las solicitudes.
     * */
    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Response<MovieData>


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") title: String
    ): Response<MovieDto>
}