import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.Serializable
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import java.util.*

@Serializable
data class Book(val uuid: String, val author: String, val title: String) {
    constructor(author: String, title: String) : this(UUID.randomUUID().toString(), author, title)
}

object BooksTable : UUIDTable() {
    val author = varchar("author", 50).index()
    val title = varchar("title", 50).index()
}

class BookEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<BookEntity>(BooksTable)

    var author by BooksTable.author
    var title by BooksTable.title
    fun toDTO(): Book = Book(id.toString(), author, title)

}

class BookService {
    fun getAll(): List<Book> = transaction {
        BookEntity.all().map { it.toDTO() }
    }

    fun getByUUID(uuid: String): Book? = transaction {
        BookEntity.findById(UUID.fromString(uuid))?.toDTO()
    }

    fun deleteByUUID(uuid: String): Book? {
        val deletedBook = getByUUID(uuid)
        transaction {
            BookEntity.findById(UUID.fromString(uuid))?.delete()
        }
        return deletedBook
    }

    fun insert(author: String, title: String): Book = transaction {
        BookEntity.new {
            this.author = author
            this.title = title
        }.toDTO()
    }

    fun updateByUUID(uuid: String, author: String? = null, title: String? = null) = transaction {
        BooksTable.update({ BooksTable.id eq UUID.fromString(uuid) }) {
            if (author != null) {
                it[this.author] = author
            }
            if (title != null) {
                it[this.title] = title
            }
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
            try {
                call.respond(myBook)
            } catch (e: Exception) {
                print(e)
            }

        }

        get("/") {
            try {
                call.respond(bookService.getAll())
            } catch (e: Exception) {
                print(e)
            }
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
