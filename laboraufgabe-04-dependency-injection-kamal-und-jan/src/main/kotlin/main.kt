import org.kodein.di.*


val di = DI {

    bindEagerSingleton { Config() }
    bindSingleton {
        val databaseUri: String = instance("databaseUri")
        Database(databaseUri, instance())
    }
    bindConstant(tag = "databaseUri") { "database://localhost" }
    bindSingleton { UserService(instance(), instance(), instance()) }
    bindSingleton { MessageService(instance(), instance(), instance()) }
    bindSingleton { Application(instance(), instance(), instance()) }
    bindProvider { Logger(instance()) }

}


fun main() {
    val application: Application by di.instance()
    application.run()
}