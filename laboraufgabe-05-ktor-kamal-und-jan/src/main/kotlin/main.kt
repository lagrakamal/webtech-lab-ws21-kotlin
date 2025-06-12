import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*
import java.util.*

data class GuestBookEntry(val username: String, val date: Date, val message: String)

val entries: MutableList<GuestBookEntry> = mutableListOf()

fun Application.mainModule() {
    install(CallLogging)

    routing {
        static {
            resources("static")
        }
        get("/") {
            call.respondHtml {
                head {
                    link {
                        rel = "stylesheet"
                        href = "/css/bootstrap.min.css"
                    }
                }

                body {
                    script { src = "/js/bootstrap.bundle.min.js" }
                    script { src = "/js/jquery-3.5.1.slim.min.js" }

                    div(classes = "container  text-center") {
                        h1 { +"Ktor Guestbook" }

                        section(classes = "guestbook") {
                            entries.forEach {
                                article {
                                    div(classes = "alert alert-primary username") {
                                        +it.username

                                    }
                                    p(classes = "alert alert-info") {
                                        +it.message
                                    }
                                    div(classes = "badge badge-secondary date") {
                                        +it.date.toString()
                                    }
                                }
                            }
                        }

                        form(method = FormMethod.post, action = "/") {

                            input(classes = "form-control form-control-lg") {
                                name = "username"
                            }

                            textArea(classes = "form-control") {
                                name = "message"
                            }
                            div(classes = "text-center") {
                                button(classes = "btn btn-primary") {
                                    +"Save"

                                }
                            }
                        }
                    }
                }
            }
        }

        post("/") {
            val postParams = call.receiveParameters()
            val username = postParams["username"] ?: throw Error("Username field is empty")
            val message = postParams["message"] ?: throw Error("Message can not be empty")

            entries.add(GuestBookEntry(username, Date(), message))

            call.respondRedirect("/")
        }

    }
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        mainModule()
    }.start(wait = true)
}