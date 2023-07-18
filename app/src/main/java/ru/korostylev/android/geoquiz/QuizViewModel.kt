package ru.korostylev.android.geoquiz

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import java.security.AccessController.getContext

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    //вызывается перед уничтожением мусора
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to destroyed")
    }

    //текущий вопрос
    var currentIndex = 0
    //использовал ли игрок кнопку чит
    var isCheater = false
    //количество верных ответов
    private var correctAnswers = 0
    //список вопросов
    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_africa, false, false)
    )
    //правильный ответ на текущий вопрос
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    //Текст текущего вопроса
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    //следующий вопрос
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    //предыдущий вопрос
    fun moveToPrev() {
        if (currentIndex != 0) {
            currentIndex = (currentIndex - 1) % questionBank.size
        } else currentIndex = questionBank.size - 1
    }
    //проверяем был ли дан ответ на текущий вопрос
    fun isAnswerGet() = questionBank[currentIndex].isAnswerGet

    //проверяем правильный ли ответ
    fun checkAnswer(userAnswer: Boolean) {
        questionBank[currentIndex].isAnswerGet = true
        val correctAnswer = questionBank[currentIndex].answer
        if (userAnswer == correctAnswer) {
            correctAnswers = correctAnswers + 1
        }
    }
    //считаем количество правильных ответов
    fun totalScore(): Int {
        var total = 0
        for (answer in questionBank) {
            if (answer.isAnswerGet == true) total += 1
        }
        if (total == questionBank.size) {
            //если количество данных ответов = длине списка вопросов, возращаем количество правильных
            return correctAnswers
        } else return 0
    }

}