class Application(
    val userService: UserService,
    val messageService: MessageService,
    val logger: Logger,
) {

    fun run() {

        print("test from Application")

    }
}