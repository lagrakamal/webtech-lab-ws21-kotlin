import org.kodein.di.bindings.InstanceBinding
import java.util.*

class Logger(
    val config: Config
) {
    private val uuid: UUID = UUID.randomUUID()
    fun log(msg: String) {
        println("[$uuid] $msg")
    }

}