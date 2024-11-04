package ar.edu.unicen.seminariomobile.ui.components.tabs

 sealed class TabItem (
     val title: String,
     val route: String
 ) {
     object PopularMovies : TabItem("Populares", "movies")
     object FavoriteMovies : TabItem("Favoritos", "favorite_movies")
}