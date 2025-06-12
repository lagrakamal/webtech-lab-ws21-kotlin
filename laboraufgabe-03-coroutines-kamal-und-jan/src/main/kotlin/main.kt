import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


suspend fun getUsernameAndEmail(id: Number): User = coroutineScope {
    val api = Api()

    val username = async { api.getUsername(id) }
    val email = async { api.getUserEmail(id) }

    return@coroutineScope User(id, username.await(), email.await())


}


suspend fun fibonacciFlow(): Flow<Int> {

    return flow {
        val fibNrs = generateSequence(Pair(0, 1))
        { Pair(it.second, it.first + it.second) }.map { it.first }
        fibNrs.forEach {
            delay(it * 1000L)
            emit(it)
        }

    }
}

fun main() = runBlocking {

    val list = fibonacciFlow().take(15).toList()
    list.forEach { print(it) }
    print(getUsernameAndEmail(1))

    Thread.sleep(2000L)
}
