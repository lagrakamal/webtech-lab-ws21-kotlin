import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.jsoup.Jsoup

class MainKtTest {
    @Test
    fun `Test ktor application`() {
        withTestApplication({ mainModule() }, {

            handleRequest(HttpMethod.Get, "/js/bootstrap.bundle.min.js").apply {  assertEquals(200, response.status()?.value, "GET /js/bootstrap.bundle.min.js") }
            handleRequest(HttpMethod.Get, "/js/jquery-3.5.1.slim.min.js").apply {  assertEquals(200, response.status()?.value, "GET /js/jquery-3.5.1.slim.min.js") }
            handleRequest(HttpMethod.Get, "/css/bootstrap.min.css").apply {  assertEquals(200, response.status()?.value, "GET /css/bootstrap.min.css") }

            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(200, response.status()?.value)
                val doc = Jsoup.parse(response.content)

                assertFalse(doc.select("SECTION.guestbook").isEmpty(), "Query for SECTION.guestbook")
                assertFalse(doc.select("FORM").isEmpty(), "Query for FORM")
                assertFalse(doc.select("INPUT[name=username]").isEmpty(), "Query for INPUT")
                assertFalse(doc.select("TEXTAREA[name=message]").isEmpty(), "Query for TEXTAREA")

                assertTrue(doc.select("SECTION.guestbook ARTICLE").isEmpty(), "SECTION.guestbook should be empty")
            }

            handleRequest(HttpMethod.Post, "/") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(listOf("username" to "testuser", "message" to "This is a test message").formUrlEncode())
            }.apply {
                assertEquals(302, response.status()?.value, "POST should redirect to /")
            }

            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(200, response.status()?.value)
                val doc = Jsoup.parse(response.content)

                assertFalse(doc.select("SECTION.guestbook ARTICLE").isEmpty(), "SECTION.guestbook should not be empty")

                val article = doc.select("SECTION.guestbook ARTICLE")
                val username = article.select("DIV.username")
                val date = article.select("DIV.date")
                val message = article.select("P")

                assertEquals("testuser", username.text())
                assertEquals("This is a test message", message.text())
                assertFalse(date.isEmpty())
            }
        })
    }
}