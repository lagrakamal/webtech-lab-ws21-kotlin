import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class MainKtTest {

    @Test
    fun `Test getUsernameAndEmail method`() = runBlockingTest {
        withTimeout(4100) {
            val res = getUsernameAndEmail(10)

            assertEquals(User(10,"user00010", "user00010@hs-flensburg.de"), res)
        }
    }

    @Test
    fun `Test fibonacciChannel method`() = runBlockingTest {
        val flow = fibonacciFlow()
        val list = flow.take(10).toList()

        assertEquals(listOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34), list)
    }
}