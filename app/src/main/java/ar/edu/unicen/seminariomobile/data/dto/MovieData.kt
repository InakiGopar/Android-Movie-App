package ar.edu.unicen.seminariomobile.data.dto

import ar.edu.unicen.seminariomobile.data.Movie
import com.google.gson.annotations.SerializedName

class MovieData (
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
) {

    fun toMovie(): Movie {
        return Movie(
            id = this.id,
            title = this.title,
            overview = this.overview,
            posterPath = this.posterPath ?: "ruta/default.jpg",
            voteAverage = this.voteAverage
        )
    }

}