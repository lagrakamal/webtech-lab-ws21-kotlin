package de.hsflensburg.webtech.lab08
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

val client = HttpClient()

@Serializable
data class UselessFact(val id: String,
                       val text: String,
                       val source: String,
                       val source_url: String,
                       val language: String,
                       val permalink: String)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        suspend fun receiveUselessFactFromApi(): UselessFact {
            val httpResponse: HttpResponse = client.get("https://uselessfacts.jsph.pl/random.json")
            val responseBody: String = httpResponse.receive()
            return Json.decodeFromString(responseBody)
        }

        suspend fun callUselessFactsApi() : UselessFact = coroutineScope {
            return@coroutineScope receiveUselessFactFromApi()
        }

        @Composable
        fun Fact(fact: UselessFact){
            Text(fact.text)
        }

        setContent {
            MaterialTheme {
                Surface {
                    var fact: UselessFact? by remember {
                        mutableStateOf(null)
                    }
                    val scope = rememberCoroutineScope()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        fact?.let { Fact(it) }
                        Button(onClick = { scope.launch {
                            fact = callUselessFactsApi()
                        } }) {
                            Text("Reload")
                        }
                    }
                }
            }
        }
    }
}
