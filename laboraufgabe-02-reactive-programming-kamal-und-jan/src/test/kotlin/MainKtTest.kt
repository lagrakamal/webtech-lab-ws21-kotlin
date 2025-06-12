import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration

class MainKtTest {
    @Test
    fun `Test generateAllPossibleNames method`() {
        val firstNames = sequenceOf("Alex", "Peter", "Julia")
        val lastNames = sequenceOf("Müller", "Jensen")

        val names = generateAllPossibleNames(firstNames, lastNames).toList()

        Assertions.assertEquals(6, names.size)

        Assertions.assertTrue(names.contains("Alex Müller"))
        Assertions.assertTrue(names.contains("Alex Jensen"))
        Assertions.assertTrue(names.contains("Peter Müller"))
        Assertions.assertTrue(names.contains("Peter Jensen"))
        Assertions.assertTrue(names.contains("Julia Müller"))
        Assertions.assertTrue(names.contains("Julia Jensen"))
    }

    @Test
    fun `Test getUsernameAndEmail method`() {
        val res = getUsernameAndEmail(5).block(Duration.ofSeconds(5))

        Assertions.assertEquals(User(5, "user00005", "user00005@hs-flensburg.de"), res)
    }

    @Test
    fun `Test fibonacciFlux method`() {
        val list = fibonacciFlux().take(10).collectList().block()

        Assertions.assertEquals(listOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34), list)
    }
}