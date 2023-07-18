package ru.korostylev.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract

private var isAnswerShown = false
private const val TAG = "CheatActivity"
//ключ для передачи данных через интент
private const val EXTRA_ANSWER_IS_TRUE = "ru.korostylev.android.geoquiz.answer_is_true"
//ключ для возвращения mainActivity данных, была ли нажата кнопка чит
const val EXTRA_ANSWER_SHOWN = "ru.korostylev.android.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false
    lateinit var answerTextView: TextView
    lateinit var showAnswerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        Log.d(TAG, "CheatActivity Started")
        //получаем значение правильности ответа из интента
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        //обработка нажатия кнопки показать ответ
        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
            isAnswerShown = true

        }
    }
    //функция для передачи из cheatactivity в mainactiv данных, была ли нажата кнопка чит
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        //когда нажимается кнопка SHOW ANSWER, CheatActiv упаковывает код результата и интент
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        //функция создает интент, настроенный дополнениями, необходимыми для CheatActivity
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                //кладем значение правильности текущего ответа по типу ключ-значение
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }

    }

    object CheatActivityContract: ActivityResultContract<Boolean, Boolean?>() {
        override fun createIntent(context: Context, input: Boolean): Intent {
            return Intent(context, CheatActivity::class.java)
                .putExtra(EXTRA_ANSWER_IS_TRUE, input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean? {
            return isAnswerShown
        }

    }

}