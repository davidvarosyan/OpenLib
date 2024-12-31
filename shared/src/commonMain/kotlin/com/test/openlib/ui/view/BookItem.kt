package com.test.openlib.ui.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.test.openlib.domain.model.Book
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp)) {
        book.cover_i?.let {
            KamelImage(
                resource = asyncPainterResource("https://covers.openlibrary.org/b/id/$it-M.jpg"),
                contentDescription = book.title,
                modifier = Modifier.size(60.dp),
                onLoading = { progress -> CircularProgressIndicator(progress) },
                animationSpec = tween()
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                book.title ?: "Unknown Title",
//                style = MaterialTheme.typography.h6,
                maxLines = 1,  // Limits the text to one line
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                book.author_name?.joinToString(", ") ?: "Unknown Author",
                maxLines = 1,  // Limits the text to one line
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}