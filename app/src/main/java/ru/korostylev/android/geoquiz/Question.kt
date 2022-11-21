package ru.korostylev.android.geoquiz

import androidx.annotation.StringRes

//класс вопросов, текст вопроса(ссылка id), какой правильный ответ, правильный ли ответ
data class Question(@StringRes val textResId: Int, val answer: Boolean, var isAnswerGet: Boolean)