import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.util.*

data class Book(
    val id: UUID,
    val title: String,
    val author: String,
    val year: Int
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItem(book: Book) {
    ListItem(
        icon = {
            Icon(
                Icons.Filled.List,
                contentDescription = "list"
            )
        },
        text = {
            Text(
                book.title + "\n"
            )
            Text(
                "\n" + book.author + ", " + book.year.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray

            )

        }
    )
}


@Composable
fun list(books: List<Book>) {
    books.forEach {
        ListItem(it)
        Divider()
    }
}


object AppState {
    val books = listOf(
        Book(UUID.randomUUID(), "Some Book", "Max Mustermann", 2021),
        Book(UUID.randomUUID(), "Some Book", "Max Mustermann", 2021),
        Book(UUID.randomUUID(), "Some Book", "Max Mustermann", 2021),
    )
}

@Preview
@Composable
fun App() {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Book List") })
            }
        ) {
            Column {
                list(AppState.books)
            }
        }

    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
