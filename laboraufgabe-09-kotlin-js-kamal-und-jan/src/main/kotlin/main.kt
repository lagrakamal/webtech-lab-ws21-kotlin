import kotlinx.browser.document
import kotlinx.dom.clear
import kotlinx.html.ButtonType
import kotlinx.html.FormMethod
import kotlinx.html.dom.append
import kotlinx.html.js.*
import kotlinx.html.submitInput
import org.w3c.dom.*
import org.w3c.dom.events.Event
import kotlin.js.Date

data class GuestBookEntry(val username: String, val date: Date, val message: String)
val entries: MutableList<GuestBookEntry> = mutableListOf()

fun main() {
    val rootElement = document.getElementById("root");
    rootElement?.run(::render)
}

fun render(root: Element) {
    root.clear()
    root.append {
        fun handleSubmit(event: Event) {
            event.preventDefault()
            val form = event.target as HTMLFormElement
            val username = (form.elements["username"] as HTMLInputElement).value
            val message = (form.elements["message"] as HTMLTextAreaElement).value

            entries.add(GuestBookEntry(username, Date(), message))
            render(root)

        }
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
                submitInput {  }
                onSubmitFunction = ::handleSubmit
            }
                }
            }
        }

