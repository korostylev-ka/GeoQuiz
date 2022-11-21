package ru.korostylev.android.geoquiz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {
    //присваиваем имена кнопкам и вьюшкам
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    //создаем ViewModel
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private fun updateQuestion() {
        val questionTextResId  = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        //присваиваем имена кнопкам и полю текста
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        //обработка нажатия на кнопку True
        trueButton.setOnClickListener {
            quizViewModel.checkAnswer(true)
            val trueAnswer = quizViewModel.currentQuestionAnswer
            if (trueAnswer == true) {
                Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT)
                    .show()
            }
            val total =  quizViewModel.totalScore()
            if (total != 0) {
                Toast.makeText(this, "Вы дали $total правильных ответов", Toast.LENGTH_SHORT)
                    .show()
            }
            trueButton.isClickable = false
            falseButton.isClickable = false

        }
        //Обработка нажатия на кнопку False
        falseButton.setOnClickListener {
            quizViewModel.checkAnswer(false)
            val total =  quizViewModel.totalScore()
            if (total != 0) {
                Toast.makeText(this, "Вы дали $total правильных ответов", Toast.LENGTH_SHORT)
                    .show()
            }
            trueButton.isClickable = false
            falseButton.isClickable = false
        }
        //Обработка нажатия на кнопку Next(следующий вопрос)
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            val isAnswerGet = quizViewModel.isAnswerGet()
            //делаем кнопки некликабельными после получения ответа
            if (isAnswerGet == true) {
                trueButton.isClickable = false
                falseButton.isClickable = false
            } else {
                trueButton.isClickable = true
                falseButton.isClickable = true
            }

            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        //Обработка нажатия на кнопку Prev(предыдущий вопрос)
        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            val isAnswerGet = quizViewModel.isAnswerGet()
            if (isAnswerGet == true) {
                trueButton.isClickable = false
                falseButton.isClickable = false
            } else {
                trueButton.isClickable = true
                falseButton.isClickable = true
            }
            updateQuestion()
        }

        updateQuestion()


    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}