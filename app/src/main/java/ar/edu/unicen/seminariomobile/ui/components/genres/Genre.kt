package ar.edu.unicen.seminariomobile.ui.components.genres

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import ar.edu.unicen.seminariomobile.R

@Composable
fun Genre(
    genre: String
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = colorResource(R.color.brandColor),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre,
            color = colorResource(R.color.white),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}