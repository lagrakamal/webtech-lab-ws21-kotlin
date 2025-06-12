import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers


class Api {

    fun getUsername(id: Number): Mono<String> {
        return Mono.fromCallable {
            Thread.sleep(4000)
            "user" + String.format("%05d", id)
        }.subscribeOn(Schedulers.parallel())
    }

    fun getUserEmail(id: Number): Mono<String> {
        return Mono.fromCallable {
            Thread.sleep(2000)
            "user" + String.format("%05d", id) + "@hs-flensburg.de"
        }.subscribeOn(Schedulers.parallel())
    }


}