package com.loxick.geoquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

const val TAG = "MainActivity"
class QuizViewModel : ViewModel() {

     var currentQuizId: Int = 0
     var points:Int = 0
}


class MainActivity : AppCompatActivity() {
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    var quizes: Array<Quiz> = arrayOf(
        Quiz("Nikita can flight?", true),
        Quiz("Kotlin cool?", false),
        Quiz("Cat is nasecomoe?", false),
        Quiz("Dog is cat?", false)
    )
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private  lateinit var quizText: TextView
    private  lateinit var poinstext: TextView
    private var isReady: Boolean = true

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
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
    override fun onCreate(savedInstanceState: Bundle?) {
        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG,"onCreate() Called")
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.button_true)
        falseButton = findViewById(R.id.button_false)
        quizText = findViewById(R.id.quizText)
        poinstext = findViewById(R.id.pointText)
        poinstext.setText("Your poins: ${quizViewModel.points}")
        UpdateQuiz()


        trueButton.setOnClickListener{
                view: View -> setAnswer(true)
        }
        falseButton.setOnClickListener{
            view: View -> setAnswer(false)
        }

    }

    private fun UpdateQuiz(){
        if (quizViewModel.currentQuizId == quizes.size){
            quizViewModel.currentQuizId = 0
            println("quiz id ${quizViewModel.currentQuizId}")
        }
        quizText.setText(quizes[quizViewModel.currentQuizId].question)
    }

    private fun setAnswer(answer:Boolean){
        if(isReady){
            isReady = false
            if(quizes[quizViewModel.currentQuizId].answer == answer) {
                quizViewModel.points++
                updateButton()
                updatePoints()
                Handler().postDelayed({
                    quizViewModel.currentQuizId ++
                    isReady = true
                    UpdateQuiz()
                }, 1000)
            } else{
                isReady = false
                updateButton()
                Handler().postDelayed({
                    isReady = true
                    quizViewModel.currentQuizId ++
                    UpdateQuiz()
                }, 1000)
            }
        } else{
            val textMessage: String = "Вы слишком много кликаете!!!!!!"
            val toast = Toast.makeText(applicationContext,textMessage,textMessage.length )
            toast.show()
        }

    }

    private fun updatePoints(){
        poinstext.setText("Your poins: ${quizViewModel.points}")
        println(quizViewModel.points)
    }


    private fun updateButton(){
        if(!quizes[quizViewModel.currentQuizId].answer){
            falseButton.setBackgroundColor(Color.RED)
            trueButton.setBackgroundColor(Color.GREEN)
            falseButton.setBackgroundColor(Color.GREEN)
            trueButton.setBackgroundColor(Color.RED)
        } else{
            falseButton.setBackgroundColor(Color.RED)
            trueButton.setBackgroundColor(Color.GREEN)

        }
        Handler().postDelayed({
            falseButton.setBackgroundColor(Color.WHITE)
            trueButton.setBackgroundColor(Color.WHITE)
        }, 1000)
    }
}