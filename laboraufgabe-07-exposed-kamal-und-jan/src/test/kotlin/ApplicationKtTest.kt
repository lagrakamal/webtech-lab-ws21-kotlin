import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.jsoup.Jsoup
import java.util.*

class ApplicationKtTest {
    @Test
    fun `Test ktor application`() {
        Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")

        transaction {
            SchemaUtils.create(BooksTable)
        }

        val randomUUID = UUID.randomUUID().toString()

        withTestApplication({ module() }, {
            var tempBookId: String

            handleRequest(HttpMethod.Get, "/$randomUUID").apply {
                assertEquals(404, response.status()?.value, "GET /$randomUUID should return 404 status code")
            }

            handleRequest(HttpMethod.Post, "/") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""
                    {"title": "Laboraufgabe 07", "author": "Boris Dudelsack"}
                """.trimIndent())
            }.apply {
                assertEquals(200, response.status()?.value, "POST should return 200 status code")

                val book = Json.decodeFromString<Book>(response.content!!)

                assertEquals("Laboraufgabe 07", book.title)
                assertEquals("Boris Dudelsack", book.author)
                assertNotEquals("", book.uuid, "Book ID should not be empty")

                tempBookId = book.uuid
            }

            handleRequest(HttpMethod.Get, "/$tempBookId").apply {
                assertEquals(200, response.status()?.value, "GET /{uuid} should return 200 status code")

                val book = Json.decodeFromString<Book>(response.content!!)

                assertEquals("Laboraufgabe 07", book.title)
                assertEquals("Boris Dudelsack", book.author)
                assertEquals(tempBookId, book.uuid)
            }

            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(200, response.status()?.value)

                val res: JsonArray = Json.decodeFromString(response.content!!)
                assertTrue(res.size >= 1, "GET / should return an array with one or more elements")
            }

            handleRequest(HttpMethod.Post, "/$tempBookId") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""
                    {"title": "Laboraufgabe 08" }
                """.trimIndent())
            }.apply {
                assertEquals(200, response.status()?.value, "POST should return 200 status code")
            }

            handleRequest(HttpMethod.Post, "/$tempBookId") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""
                    {"author": "Dudelsack, Boris" }
                """.trimIndent())
            }.apply {
                assertEquals(200, response.status()?.value, "POST /{uuid} should return 200 status code")
            }

            handleRequest(HttpMethod.Get, "/$tempBookId").apply {
                assertEquals(200, response.status()?.value, "GET /{uuid} should return 200 status code")

                val book = Json.decodeFromString<Book>(response.content!!)

                assertEquals("Laboraufgabe 08", book.title)
                assertEquals("Dudelsack, Boris", book.author)
                assertEquals(tempBookId, book.uuid)
            }

            handleRequest(HttpMethod.Delete, "/$tempBookId").apply {
                assertEquals(200, response.status()?.value, "DELETE /{uuid} should return 200 status code")

                val book = Json.decodeFromString<Book>(response.content!!)

                assertEquals("Laboraufgabe 08", book.title)
                assertEquals("Dudelsack, Boris", book.author)
                assertEquals(tempBookId, book.uuid)
            }


            handleRequest(HttpMethod.Get, "/$tempBookId").apply {
                assertEquals(404, response.status()?.value)
            }
        })
    }
}