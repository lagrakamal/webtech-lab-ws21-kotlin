import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class MainKtTest {
    @Test
    fun `Test the Student data class`() {
        val student = Student(
            firstName = "Max",
            lastName = "Mustermann",
            matriculationNumber = 10001
        )
        Assertions.assertEquals(student.firstName, "Max")
        Assertions.assertEquals(student.lastName, "Mustermann")
        Assertions.assertEquals(student.matriculationNumber, 10001)
    }

    @Test
    fun `Test generateRandomStudents method`() {
        val students = generateRandomStudents(10)
        Assertions.assertEquals(students.size, 10)
    }

    @Test
    fun `Test scoreStudents method`() {
        val students = generateRandomStudents(100)
        val scores = scoreStudents(students)
        Assertions.assertEquals(scores.size, 100)
        Assertions.assertTrue(scores is Map<Student, Int>)
    }
}
