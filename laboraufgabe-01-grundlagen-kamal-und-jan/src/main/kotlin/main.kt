fun main() {
    val students = generateRandomStudents(10);
    students.forEach {
        print(it)
    }
    scoreStudents(students).forEach {
        print(it)
    }
}

fun generateRandomStudents(n: Int): List<Student> {
    val firsNames = listOf(
        "Rick", "Ridwan", "Callan", "Axl", "Roman", "Macauley", "Zainab", "Glen", "Morty", "Carwyn",
        "Kim", "Petra", "Flynn", "Minnie", "Derek", "Aran", "Louise", "Hajrah"
    )
    val lastNames = listOf(
        "Charlton", "Dean", "Fuentes", "Humphries", "Mcclain", "Sharp", "Robbins", "Swanson", "Sanchez",
        "Arnold", "Mccarthy", "Winter", "Golden", "Orr", "Agadir", "Glam", "Ahraf", "Awid"
    )
    return List(n) { id: Int -> Student(firsNames.random(), lastNames.random(), id) }
}

fun scoreStudents(students: List<Student>): Map<Student, Int> {
    return students.associateWith { (1 until 100).random() }
}