package ar.edu.unicen.seminariomobile.ui.components.nav

import androidx.compose.foundation.Image
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ar.edu.unicen.seminariomobile.R
import ar.edu.unicen.seminariomobile.viewModel.MovieViewModel

@Composable
fun AppNav(
    tabIndex: MutableState<Int>,
    navController: NavController,
    movieViewModel: MovieViewModel
) {

    // Observar la pila de navegación para actualizar el tab cuando cambie de pantalla
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Detectar cambios en la ruta de navegación y actualizar el tab
    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            "movies" -> tabIndex.value = 0
            "favorites" -> tabIndex.value = 1
        }
    }

    TabRow(
        selectedTabIndex = tabIndex.value,
        containerColor = colorResource(id = R.color.brandColor),
        contentColor = colorResource(id = R.color.white),
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[tabIndex.value]),
                color = colorResource(id = R.color.white)
            )
        }
    ) {
        Tab(
            selected = tabIndex.value == 0,
            onClick = {
                tabIndex.value = 0
                // Navega a la pantalla de "Películas Populares"
                navController.navigate("movies")
                movieViewModel.searchMovies("") //si el usuario hace click en peliculas populares se llama a buscar peliculas sin parametros, que tare todas las peliculas
            },
            text = { Text(
                stringResource(id = R.string.popular)
            ) },
            icon = {
                Image(
                    painter = painterResource(R.drawable.baseline_local_fire_department_24),
                    colorFilter =  ColorFilter.tint(colorResource(id = R.color.white)),
                    contentDescription = null,
                )
            },
            selectedContentColor = colorResource(id = R.color.white)
        )
        Tab(
            selected = tabIndex.value == 1,
            onClick = {
                tabIndex.value = 1
                navController.navigate("favorites") // Navega a la pantalla de "Películas Favoritas"
            },
            text = {
                Text(
                stringResource(id = R.string.favorites)
                )
           },
            icon = {
                Image(
                    painter = painterResource(R.drawable.ic_popular),
                    colorFilter =  ColorFilter.tint(colorResource(id = R.color.white)),
                    contentDescription = null,
                )
            },

            selectedContentColor = colorResource(id = R.color.white)
        )
    }
}