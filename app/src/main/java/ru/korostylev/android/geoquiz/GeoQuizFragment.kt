package ru.korostylev.android.geoquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels

//добавляем ключ для пары ключ-значение при onSaveInstanceState
private const val KEY_INDEX = "index"
//код запроса, для определения, какой из потомков активити возвращает данные
private const val REQUEST_CODE_CHEAT = 0


class GeoQuizFragment : Fragment() {
    //присваиваем имена кнопкам и вьюшкам
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button
    //создаем ViewModel
    private val quizViewModel: QuizViewModel by activityViewModels()
    //"старый" вариант
    /*private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }*/
    val cheatActivityLauncher = registerForActivityResult(CheatActivity.CheatActivityContract) {result->
        println(result)
        if (result == true) {
            Toast.makeText(requireContext(), "ЧИТЕР!!!", Toast.LENGTH_LONG)
                .show()
        }

    }

    private fun updateQuestion() {
        val questionTextResId  = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_geo_quiz, container, false)
        //присваиваем переменной значение текущего номера вопросов, если activity была перезапущена
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        //во viewmodel присваеваем значение текущего индекса
        quizViewModel.currentIndex= currentIndex
        //присваиваем имена кнопкам и полю текста
        trueButton = view.findViewById(R.id.true_button)
        falseButton = view.findViewById(R.id.false_button)
        nextButton = view.findViewById(R.id.next_button)
        prevButton = view.findViewById(R.id.prev_button)
        questionTextView = view.findViewById(R.id.question_text_view)
        cheatButton = view.findViewById(R.id.cheat_button)
        //обработка нажатия на кнопку True
        trueButton.setOnClickListener {
            quizViewModel.checkAnswer(true)
            val trueAnswer = quizViewModel.currentQuestionAnswer
            if (trueAnswer == true) {
                Toast.makeText(requireContext(), R.string.correct_toast, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT)
                    .show()
            }
            val total =  quizViewModel.totalScore()
            if (total != 0) {
                Toast.makeText(requireContext(), "Вы дали $total правильных ответов", Toast.LENGTH_SHORT)
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
                Toast.makeText(requireContext(), "Вы дали $total правильных ответов", Toast.LENGTH_SHORT)
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
            Toast.makeText(requireContext(), "YOU ARE CHEATER", Toast.LENGTH_LONG)
        }

        updateQuestion()
        // Inflate the layout for this fragment
        return view
    }



}