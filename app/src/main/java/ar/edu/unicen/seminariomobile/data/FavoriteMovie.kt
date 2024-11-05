package ar.edu.unicen.seminariomobile.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unicen.seminariomobile.data.dto.Genre

@Entity(tableName = "favorite_movies")
data class FavoriteMovie (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "movie_title")
    val title: String,

    @ColumnInfo("overview")
    val overview: String,

    @ColumnInfo(name = "movie_poster")
    val posterPath: String?,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "genres")
    val genres: List<Genre>
) {


    fun toMovie() : Movie {
        return Movie(
            id = this.id,
            title = this.title,
            overview = this.overview,
            posterPath = this.posterPath.toString(),
            voteAverage = this.voteAverage,
            genres = this.genres.map { Genre(it.id, it.name) }
        )
    }


    companion object {
        fun Movie.toFavoriteMovie(): FavoriteMovie {
            return FavoriteMovie(
                    id = this.id,
                    title = this.title,
                    overview = this.overview,
                    posterPath = this.posterPath.toString(),
                    voteAverage = this.voteAverage,
                    genres = this.genres.map { Genre(it.id, it.name) }
            )
        }
    }
}
