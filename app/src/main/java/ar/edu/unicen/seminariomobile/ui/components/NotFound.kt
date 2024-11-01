package ar.edu.unicen.seminariomobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.seminariomobile.R

@Composable
fun NotFound() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, // Centrar verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centrar horizontalmente
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_not_found),
            contentDescription = null,
            colorFilter = ColorFilter.tint(colorResource(id = R.color.brandColor)),
            modifier = Modifier.size(70.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.movie_not_found),
                color = colorResource(id = R.color.textPrimaryColor),
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }

}


@Composable
@Preview(showBackground = true)
private fun NotFoundPreview() {
    NotFound()
}