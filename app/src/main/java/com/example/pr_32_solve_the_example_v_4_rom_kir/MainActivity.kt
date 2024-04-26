package com.example.pr_32_solve_the_example_v_4_rom_kir

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import kotlin.math.round
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var checkButton: Button
    private lateinit var newExampleButton: Button
    private lateinit var operand1TextView: TextView
    private lateinit var operand2TextView: TextView
    private lateinit var operatorTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var statsTextView: TextView

    private var correctAnswers = 0
    private var totalAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)
        checkButton = findViewById(R.id.checkButton)
        newExampleButton = findViewById(R.id.newExampleButton)
        operand1TextView = findViewById(R.id.operand1TextView)
        operand2TextView = findViewById(R.id.operand2TextView)
        operatorTextView = findViewById(R.id.operatorTextView)
        answerEditText = findViewById(R.id.answerEditText)
        statsTextView = findViewById(R.id.statsTextView)

        startButton.setOnClickListener { generateExample() }
        checkButton.setOnClickListener { checkAnswer() }
        newExampleButton.setOnClickListener { generateExample() }
    }

    private fun generateExample() {
        val operand1 = Random.nextInt(10, 99)
        val operand2 = Random.nextInt(10, 99)
        val operator = when (Random.nextInt(0, 4)) {
            0 -> "*"
            1 -> "/"
            2 -> "-"
            else -> "+"
        }

        operand1TextView.text = operand1.toString()
        operand2TextView.text = operand2.toString()
        operatorTextView.text = operator

        when (operator) {
            "*" -> answerEditText.setText((operand1 * operand2).toString())
            "/" -> {
                val result = operand1 / operand2
                answerEditText.setText(result.toString())
            }
            "-" -> answerEditText.setText((operand1 - operand2).toString())
            "+" -> answerEditText.setText((operand1 + operand2).toString())
        }

        startButton.isEnabled = false
        checkButton.isEnabled = true
        newExampleButton.isEnabled = false
    }

    private fun checkAnswer() {
        val userAnswer = answerEditText.text.toString().toIntOrNull()
        val correctAnswer = when (operatorTextView.text) {
            "*" -> operand1TextView.text.toString().toInt() * operand2TextView.text.toString().toInt()
            "/" -> operand1TextView.text.toString().toInt() / operand2TextView.text.toString().toInt()
            "-" -> operand1TextView.text.toString().toInt() - operand2TextView.text.toString().toInt()
            "+" -> operand1TextView.text.toString().toInt() + operand2TextView.text.toString().toInt()
            else -> 0
        }

        if (userAnswer == correctAnswer) {
            operand1TextView.setBackgroundColor(android.graphics.Color.GREEN)
            correctAnswers++
        } else {
            operand1TextView.setBackgroundColor(android.graphics.Color.RED)
        }

        totalAnswers++
        statsTextView.text = "Правильных ответов: $correctAnswers из $totalAnswers (${(correctAnswers.toDouble() / totalAnswers * 100).roundToInt()}%)"


        answerEditText.isEnabled = false
        checkButton.isEnabled = false
        newExampleButton.isEnabled = true
    }
}
