package pl.filked.triptrop.data

data class QuizQuestion(
    val id: Int,
    val question: String,
    val answers: List<String>,
    val correctAnswer: Int,
    val difficulty: String,
    val year: String,
    val category: String
)