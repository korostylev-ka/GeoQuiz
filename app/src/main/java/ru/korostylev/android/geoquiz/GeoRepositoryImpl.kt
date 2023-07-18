package ru.korostylev.android.geoquiz

class GeoRepositoryImpl: GeoRepository {
    override val listOfQuestions: List<Question>
        get() = listOf(
            Question(R.string.question_australia, true, false),
            Question(R.string.question_oceans, true, false),
            Question(R.string.question_africa, false, false)
        )
}