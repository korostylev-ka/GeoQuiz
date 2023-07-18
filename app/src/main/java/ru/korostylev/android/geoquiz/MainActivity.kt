package ru.korostylev.android.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

private const val TAG = "MainActivity"
//добавляем ключ для пары ключ-значение при onSaveInstanceState
private const val KEY_INDEX = "index"
//код запроса, для определения, какой из потомков активити возвращает данные
private const val REQUEST_CODE_CHEAT = 0


class MainActivity : AppCompatActivity() {
    //присваиваем имена кнопкам и вьюшкам
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button
    //создаем ViewModel
    private val quizViewModel: QuizViewModel by viewModels()
    //"старый" вариант
    /*private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }*/

    private fun updateQuestion() {
        val questionTextResId  = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    val cheatActivityLauncher = registerForActivityResult(CheatActivity.CheatActivityContract) {result->
        println(result)
        if (result == true) {
            Toast.makeText(this, "ЧИТЕР!!!", Toast.LENGTH_LONG)
                .show()
        }

    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        //присваиваем переменной значение текущего номера вопросов, если activity была перезапущена
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        //во viewmodel присваеваем значение текущего индекса
        quizViewModel.currentIndex= currentIndex
        //присваиваем имена кнопкам и полю текста
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_button)
        //обработка нажатия на кнопку True
        trueButton.setOnClickListener {
            quizViewModel.checkAnswer(true)
            val trueAnswer = quizViewModel.currentQuestionAnswer
            if (trueAnswer == true) {
                trueButton.setBackgroundColor(R.color.correct)
                Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)
                    .show()
            } else {
                trueButton.setBackgroundColor(R.color.correct)
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
        //обработка нажатия кнопки чит
        cheatButton.setOnClickListener {
            /*//интент для запуска активити с читом(старый вариант)
            val intent = Intent(this, CheatActivity::class.java)*/
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            //создаем интент и кладем значение правильности текущего ответа
            //val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //запуск активити чита по интенту
            //startActivity(intent)
            //запуск активити чита с передачей информации от дочерней активити @Deprecated!!!
            //2 параметр - код запроса - целое число, для определения, кто из потомков передает данные
           //startActivityForResult(intent, REQUEST_CODE_CHEAT)
            cheatActivityLauncher.launch(answerIsTrue)
            Toast.makeText(this@MainActivity, "YOU ARE CHEATER", Toast.LENGTH_LONG)
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
    //по умолчаниюфункция сохраняет все представления и состояния активити в объекте bundle
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        //кладеи в bundle значение текущего индекса вопросов
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
    //переопределяем для получения кода было ли читерство из CheatActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
}