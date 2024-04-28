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
import android.text.TextWatcher
import android.text.Editable




class MainActivity : AppCompatActivity() {
    // Инициализация переменных для кнопок
    private lateinit var startButton: Button
    private lateinit var checkButton: Button
    private lateinit var newExampleButton: Button
    private lateinit var restartButton: Button
    // Инициализация переменных для TextView, отображающих операнды и оператор
    //private lateinit var operand1TextView: TextView
    //private lateinit var operand2TextView: TextView
    //private lateinit var operatorTextView: TextView
    // Инициализация переменной для поля ввода ответа
    //private lateinit var answerEditText: EditText
    // Инициализация переменной для TextView, отображающего статистику
    private lateinit var statsTextView: TextView
    // Переменные для подсчета правильных и общего количества ответов
    private var correctAnswers = 0
    private var totalAnswers = 0
    // Объявление переменных в классе, чтобы они были доступны в обеих функциях
    private lateinit var operand1TextView: TextView
    private lateinit var operand2TextView: TextView
    private lateinit var operatorTextView: TextView
    private lateinit var answerEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)// Вызов метода onCreate суперкласса
        setContentView(R.layout.activity_main)// Установка макета для активности

        // Инициализация переменных для работы с элементами интерфейса
        startButton = findViewById(R.id.startButton)
        checkButton = findViewById(R.id.checkButton)
        restartButton = findViewById(R.id.restartButton)
        newExampleButton = findViewById(R.id.newExampleButton)
        operand1TextView = findViewById(R.id.operand1TextView)
        operand2TextView = findViewById(R.id.operand2TextView)
        operatorTextView = findViewById(R.id.operatorTextView)
        answerEditText = findViewById(R.id.answerEditText)
        statsTextView = findViewById(R.id.statsTextView)

        answerEditText.isEnabled = false
        startButton.isEnabled = true
        checkButton.isEnabled = false
        newExampleButton.isEnabled = false
        restartButton.isEnabled = false

        // Установка обработчиков событий для кнопок
        startButton.setOnClickListener { generateExample() }// Генерация нового примера при нажатии на кнопку "СТАРТ"
        checkButton.setOnClickListener { checkAnswer() } // Проверка ответа при нажатии на кнопку "ПРОВЕРКА"
        newExampleButton.setOnClickListener { generateExample() } // Генерация нового примера при нажатии на кнопку "Новый пример"
        restartButton.setOnClickListener {
            correctAnswers = 0
            totalAnswers = 0
            statsTextView.text = "Правильных ответов: $correctAnswers из $totalAnswers ($totalAnswers%)"
            generateExample() }// Генерация нового примера при нажатии на кнопку "REСТАРТ"
    }

    private fun generateExample() {
        answerEditText.getText().clear()
        // Установка белого фона для TextView, отображающего операнды
        operand1TextView.setBackgroundColor(android.graphics.Color.WHITE)

        // Генерация случайных чисел для операндов в диапазоне от 10 до 99
        val operand1 = Random.nextInt(10, 99)
        val operand2 = Random.nextInt(10, 99)
        // Выбор случайного оператора из списка: *, /, -, +
        val operator = when (Random.nextInt(0, 4)) {
            0 -> "*"
            1 -> if (operand1 % operand2 == 0) "/" else "+" // Проверка на целочисленность результата деления
            2 -> "-"
            else -> "+"
        }

        // Отображение сгенерированных чисел и оператора в TextView
        operand1TextView.text = operand1.toString()
        operand2TextView.text = operand2.toString()
        operatorTextView.text = operator

        // Отключение кнопки checkButton, если answerEditText пуст
        checkButton.isEnabled = answerEditText.text.isNotEmpty()


        answerEditText.isEnabled = true
        startButton.isEnabled = false
        checkButton.isEnabled = false
        newExampleButton.isEnabled = false
        restartButton.isEnabled = false

        // Добавление слушателя текста для answerEditText
        answerEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Включение или отключение кнопки checkButton в зависимости от содержимого answerEditText
                checkButton.isEnabled = s?.isNotEmpty() == true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                checkButton.isEnabled = false
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkButton.isEnabled = true
            }
        })


        // Выполнение соответствующей математической операции и установка результата в EditText
        //resheie(operand1, operand2, operator)
    }


    private fun resheie(operand1: Int, operand2: Int, operator: String)
    {
        when (operator)
        {
            "*" -> answerEditText.setText((operand1 * operand2).toString())
            "/" -> {
                val result = operand1 / operand2
                answerEditText.setText(result.toString())
            }
            "-" -> answerEditText.setText((operand1 - operand2).toString())
            "+" -> answerEditText.setText((operand1 + operand2).toString())
        }


        answerEditText.isEnabled = false
        startButton.isEnabled = false
        checkButton.isEnabled = false
        newExampleButton.isEnabled = true
        restartButton.isEnabled = true
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
            val operand1 = operand1TextView.text.toString().toInt()
            val operand2 = operand2TextView.text.toString().toInt()
            val operator = operatorTextView.text.toString()
            // Выполнение соответствующей математической операции и установка результата в EditText
            resheie(operand1, operand2, operator)
            operand1TextView.setBackgroundColor(android.graphics.Color.RED)
        }

        totalAnswers++
        statsTextView.text = "Правильных ответов: $correctAnswers из $totalAnswers (${(correctAnswers.toDouble() / totalAnswers * 100).roundToInt()}%)"


        answerEditText.isEnabled = false
        startButton.isEnabled = false
        checkButton.isEnabled = false
        newExampleButton.isEnabled = true
        restartButton.isEnabled = true
    }
}
