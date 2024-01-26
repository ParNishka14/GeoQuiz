package com.loxick.geoquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private  lateinit var quizText: TextView
    private  lateinit var poinstext: TextView
    private var currentQiuzId: Int = 0
    private var points:Int = 0
    private var isReady: Boolean = true
    private var quizes: Array<Quiz> = arrayOf(
        Quiz("Nikita can flight?", true),
        Quiz("Kotlin cool?", false),
        Quiz("Cat is nasecomoe?", false),
        Quiz("Dog is cat?", false)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.button_true)
        falseButton = findViewById(R.id.button_false)
        quizText = findViewById(R.id.quizText)
        poinstext = findViewById(R.id.pointText)
        poinstext.setText("Your poins: $points")
        UpdateQuiz()


        trueButton.setOnClickListener{
                view: View -> setAnswer(true)
        }
        falseButton.setOnClickListener{
            view: View -> setAnswer(false)
        }

    }

    private fun UpdateQuiz(){
        if (currentQiuzId == quizes.size){
            currentQiuzId = 0
            println("quiz id $currentQiuzId")
        }
        quizText.setText(quizes[currentQiuzId].question)
    }

    private fun setAnswer(answer:Boolean){
        if(isReady){
            isReady = false
            if(quizes[currentQiuzId].answer == answer) {
                points++
                updateButton()
                updatePoints()
                Handler().postDelayed({
                    currentQiuzId ++
                    isReady = true
                    UpdateQuiz()
                }, 1000)
            } else{
                isReady = false
                updateButton()
                Handler().postDelayed({
                    isReady = true
                    currentQiuzId ++
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
        poinstext.setText("Your poins: $points")
        println(points)
    }


    private fun updateButton(){
        if(!quizes[currentQiuzId].answer){
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