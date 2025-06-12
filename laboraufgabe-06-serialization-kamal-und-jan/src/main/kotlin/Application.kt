import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Book(val uuid: String, val author: String, val title: String) {
    constructor(author: String, title: String) : this(UUID.randomUUID().toString(), author, title)
}

@Serializable
class BookService {
    private val storage: MutableMap<String, Book> = mutableMapOf()

    fun getAll(): List<Book> = storage.values.toList()
    fun getByUUID(uuid: String): Book? = storage.get(uuid)
    fun deleteByUUID(uuid: String): Book? = storage.remove(uuid)

    fun insert(author: String, title: String): Book {
        val book = Book(author, title)
        storage[book.uuid] = book
        return book
    }

    fun updateByUUID(uuid: String, author: String? = null, title: String? = null) {
        storage[uuid]?.let { book ->
            storage[uuid] = book.copy(author = author ?: book.author, title = title ?: book.title)
        }
    }
}

fun Application.module() {

    install(ContentNegotiation) { json() }

    di {
        bindSingleton { BookService() }
    }

    routing {

        val bookService: BookService by closestDI().instance()
        post("/") {
            @Serializable
            data class InsertBookRequest(val author: String, val title: String)

            val insertTheBook = call.receive<InsertBookRequest>()
            val myBook = bookService.insert(insertTheBook.author, insertTheBook.title)
            call.respond(myBook)
        }

        get("/") {
            call.respond(bookService.getAll())
        }

        post("/{uuid}") {
            val uuid = call.parameters["uuid"]

            @Serializable
            data class ChangeBookRequest(val author: String? = null, val title: String? = null)

            @Serializable
            data class ChangeBookResponse(val success: Boolean)

            val changeBook = call.receive<ChangeBookRequest>()
            bookService.updateByUUID(uuid.toString(), changeBook.author, changeBook.title)
            call.respond(ChangeBookResponse(true))
        }

        get("/{uuid}") {

            val uuid = call.parameters["uuid"]
            val myBook = bookService.getByUUID(uuid.toString())

            if (myBook != null) {
                try {
                    call.respond(myBook)
                } catch (e: Exception) {
                    print(e)
                }
            }
        }
        delete("/{uuid}") {

            val uuid = call.parameters["uuid"]
            val myBook: Book? = bookService.deleteByUUID(uuid.toString())

            if (myBook != null) {
                try {
                    call.respond(myBook)
                } catch (e: Exception) {
                    print(e)
                }
            }

        }
    }
}

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        watchPaths = listOf("classes", "resources"),
        module = Application::module
    ).apply {
        start(wait = true)
    }
}
