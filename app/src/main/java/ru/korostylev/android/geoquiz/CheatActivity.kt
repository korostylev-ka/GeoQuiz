package ru.korostylev.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//ключ для передачи данных через интент
private const val EXTRA_ANSWER_IS_TRUE = "ru.korostylev.android.geoquiz.answer_is_true"
class CheatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
    }
}