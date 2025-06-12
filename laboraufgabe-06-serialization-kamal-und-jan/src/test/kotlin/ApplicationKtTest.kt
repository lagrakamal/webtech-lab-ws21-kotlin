import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.jsoup.Jsoup

@ExperimentalSerializationApi
class ApplicationKtTest {
    @Test
    fun `Test ktor application`() {
        withTestApplication({ module() }, {
            var tempBookId: String

            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(200, response.status()?.value)

                val res: JsonArray = Json.decodeFromString(response.content!!)
                assertEquals(0, res.size, "GET / should result in an empty json array")
            }

            handleRequest(HttpMethod.Get, "/blafasel").apply {
                assertEquals(404, response.status()?.value, "GET /blafasel should return 404 status code")
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
                assertEquals(1, res.size, "GET / should return an array with one element")

                val book = Json.decodeFromJsonElement<Book>(res[0])

                assertEquals("Laboraufgabe 07", book.title)
                assertEquals("Boris Dudelsack", book.author)
                assertEquals(tempBookId, book.uuid)
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


            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(200, response.status()?.value)

                val res: JsonArray = Json.decodeFromString(response.content!!)
                assertEquals(0, res.size, "GET / should result in an empty json array")
            }
        })
    }
}
