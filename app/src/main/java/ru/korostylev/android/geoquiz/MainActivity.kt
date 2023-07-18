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


class MainActivity : AppCompatActivity() {






    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, GeoQuizFragment())
            .commit()


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
        /*Log.i(TAG, "onSaveInstanceState")
        //кладеи в bundle значение текущего индекса вопросов
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)*/
    }
    //переопределяем для получения кода было ли читерство из CheatActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }*/
    }
}