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


    /**
     * Endpoint que maneja la busqueda de peliculas
     *
     * @query api_key: pasar clave de API para autenticar las solicitudes.
     * @query query: En este parametro va la pelicula que se desea buscar.
     * @query page: Especifica la página de resultados que deseas obtener en base a la busqueda.
     *
     * */
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") title: String,
        @Query("page") page: Int
    ): Response<MovieDto>


    /**
     * Endpoint que trae las peliculas que estan en tendencia
     *
     * @path time_window: especifica si quieres traer las tendencias del dia o de la semana
     * @query api_key: pasar clave de API para autenticar las solicitudes.
     *
     * */
    @GET("trending/movie/{time_window}")
    suspend fun getTrendMovies(
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String
    ): Response<MovieDto>

}