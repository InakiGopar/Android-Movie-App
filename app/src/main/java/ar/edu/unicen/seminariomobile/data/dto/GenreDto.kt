package ar.edu.unicen.seminariomobile.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

data class GenreDTto(
    val id: Int,
    val name: String
)

data class Genre(
    val id: Int,
    val name: String
)

@Entity(tableName = "genres")
data class GenreDb(
    @PrimaryKey val id: Int,
    val name: String
)