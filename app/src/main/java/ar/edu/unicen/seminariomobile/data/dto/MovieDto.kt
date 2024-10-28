package ar.edu.unicen.seminariomobile.data.dto

import com.google.gson.annotations.SerializedName

 class MovieDto (
     @SerializedName("results")
     val results: List<MovieData>
 )


