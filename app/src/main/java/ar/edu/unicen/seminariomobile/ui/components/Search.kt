package ar.edu.unicen.seminariomobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.seminariomobile.R

@Composable
fun Search(
    searchText: String,
    setSearchText: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Campo de texto para buscar peliculas
            OutlinedTextField(
                value = searchText,
                onValueChange = setSearchText,
                modifier = Modifier
                    .width(300.dp)
                    .padding(end = 8.dp),
                textStyle = TextStyle(
                    color = colorResource(id = R.color.textPrimaryColor),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.None
                ),
                maxLines = 1,
                placeholder = { Text(
                    stringResource(id = R.string.search_placeholder),
                    color = colorResource(id = R.color.placheholder_text),
                    modifier = Modifier.padding(start = 5.dp),
                ) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(id = R.color.outlinedTextField_unfocus),
                    focusedContainerColor = colorResource(id = R.color.outlinedTextField_focus),
                    focusedIndicatorColor = colorResource(id = R.color.brandColor),
                    unfocusedIndicatorColor = colorResource(id = R.color.outlinedTextField_unfocus),
                    cursorColor = colorResource(id = R.color.textPrimaryColor),
                    unfocusedPlaceholderColor = colorResource(id = R.color.textPrimaryColor),
                ),
                shape = RoundedCornerShape(20.dp),
            )

            // Botón de búsqueda
            TextButton(
                onClick = onSearchClick,
                modifier = Modifier.height(50.dp).width(50.dp),
                colors = ButtonColors(
                    containerColor = colorResource(id = R.color.btnBackgroundColor),
                    contentColor = colorResource(id = R.color.btnContentColor),
                    disabledContentColor =  Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}