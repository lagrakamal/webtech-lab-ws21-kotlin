import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.kodein.di.instance

class MainKtTest {
    @Test
    fun `Test DI providing correct instances`() {
        val config: Config by di.instance()
        val userService: UserService by di.instance()
        val messageService: MessageService by di.instance()
        val database: Database by di.instance()
        val logger: Logger by di.instance()
        val application: Application by di.instance()


        val config2: Config by di.instance()
        val userService2: UserService by di.instance()
        val messageService2: MessageService by di.instance()
        val database2: Database by di.instance()
        val logger2: Logger by di.instance()
        val application2: Application by di.instance()


        assertEquals(config, config2)
        assertEquals(userService, userService2)
        assertEquals(messageService, messageService2)
        assertEquals(database, database2)
        assertEquals(application, application2)

        assertNotEquals(logger, logger2)
    }

    @Test
    fun `Test dependencies`() {
        val config: Config by di.instance()
        val userService: UserService by di.instance()
        val messageService: MessageService by di.instance()
        val database: Database by di.instance()
        val logger: Logger by di.instance()
        val application: Application by di.instance()
        val databaseUri: String by di.instance(tag = "databaseUri")

        assertEquals(userService, application.userService)
        assertEquals(messageService, application.messageService)
        assertEquals(config, application.messageService.config)
        assertEquals(config, application.userService.config)
        assertEquals(database, application.messageService.database)
        assertEquals(database, application.userService.database)
        assertEquals(config, application.userService.database.config)

        assertEquals(database.databaseUri, databaseUri)

        assertNotEquals(logger, application.messageService.logger)
        assertNotEquals(logger, application.userService.logger)
    }
}