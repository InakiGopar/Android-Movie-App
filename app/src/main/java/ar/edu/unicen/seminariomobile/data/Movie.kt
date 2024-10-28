package ar.edu.unicen.seminariomobile.data

import ar.edu.unicen.seminariomobile.data.dto.Genre

/**
 * Esta clase representa una entidad Pelicula
 * */

data class Movie (
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double,
    val genres: List<Genre> = emptyList()
)

