import kotlinx.coroutines.delay

class Api {
    suspend fun getUsername(id: Number): String {
        delay(4000L)
        return "user" + String.format("%05d", id)
    }

    suspend fun getUserEmail(id: Number): String {
        delay(2000L)
        return "user" + String.format("%05d", id) + "@hs-flensburg.de"
    }

}