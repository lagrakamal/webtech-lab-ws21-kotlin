import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun generateAllPossibleNames(firstNames: Sequence<String>, lastNames: Sequence<String>): Sequence<String> {
    return firstNames.flatMap { firstName ->
        lastNames.map { lastName ->
            "$firstName $lastName"
        }
    }
}


fun getUsernameAndEmail(id: Number): Mono<User> {
    val api = Api()

    val username = api.getUsername(id).log()
    val email = api.getUserEmail(id).log()
    return Mono.zip(username, email).map { User(id, it.t1, it.t2) }
}

fun fibonacciFlux(): Flux<Number> = Flux.generate({ Pair(0, 1) }, { pair, sink ->
    sink.next(pair.first)
    Pair(pair.second, pair.second + pair.first)
})

fun main() {
    fibonacciFlux().take(10).subscribe {
        println(it)
    }
    val firstNames = sequenceOf("Alex", "Peter", "Julia")
    val lastNames = sequenceOf("Müller", "Jensen")
    val names = generateAllPossibleNames(firstNames, lastNames).toList()
    println(names)
}