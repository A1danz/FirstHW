package com.itis.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.lang.NumberFormatException
import java.util.LinkedList

class MainActivity : AppCompatActivity() {
    private var clickButton : Button? = null;
    private val bmiValues :  HashMap<Pair<Int, Int>, Pair<Double, Double>> = createNormalValueForBMI()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clickButton = findViewById(R.id.button)
        clickButton?.setOnClickListener {
            calcucalte()
        }

    }

    private fun calcucalte() {
        val resultTextView : TextView = findViewById(R.id.resultTextView)

        var resultText : String = ""

        val nameInput : TextInputEditText = findViewById(R.id.nameInput)
        val name : String = nameInput.text.toString()

        if (name.length <= 0 || name.length > 50) {
            resultText = "Поле '${nameInput.hint}' заполнено неправильно"
        }

        Log.e("TEST", resultText)

        val heightInput : TextInputEditText = findViewById(R.id.heightInput)
        var height: Float = 0F
        try {
            height = heightInput.text.toString().toFloat() / 100
        } catch (err : NumberFormatException) {
            resultText = "Поле '${heightInput.hint}' содержит символы."
        }
        if (resultText.length != 0 && (height <= 0 || height > 250)) {
            resultText = "Поле '${heightInput.hint}' заполнено неправильно"
        }

        val weightInput : TextInputEditText = findViewById(R.id.weightInput)
        var weight: Float = 0.0F
        try {
            weight = weightInput.text.toString().toFloat()
        } catch (err : NumberFormatException) {
            resultText = "Поле '${weightInput.hint}' содержит символы."
        }
        if (resultText.length != 0 && (weight <= 0 || weight > 250)) {
            resultText = "Поле '${weightInput.hint}' заполнено неправильно"
        }

        val ageInput : TextInputEditText = findViewById(R.id.ageInput)
        var age: Int = 0
        try {
            age = ageInput.text.toString().toInt()
        } catch (err : NumberFormatException) {
            resultText = "Поле '${ageInput.hint}' содержит символы."
        }

        if (resultText.length != 0 && (age <= 0 || age > 250)) {
            resultText = "Поле '${ageInput.hint}' заполнено неверно."
        }

        if (resultText.length == 0) {
            val BMI : Float = calcucalteBMI(height, weight)
            resultText = createResultByBMI(age, BMI)

        }
        resultTextView.setText(resultText)
    }

    private fun calcucalteBMI(height: Float, weight: Float) : Float {
        Log.e("WEIGHT", weight.toString())
        Log.e("HEIGHT", weight.toString())
        return weight / (height * height)
    }

    private fun createResultByBMI(age : Int, BMI : Float) : String {
        val keys = bmiValues.keys
        for (key in keys) {
            if (key.first <= age && age <= key.second) {
                val firstValue = bmiValues.getValue(key).first
                val secondValue = bmiValues.getValue(key).second
                if (firstValue <= BMI && BMI <= secondValue) {
                    return "Ваш ИМТ в норме."
                }
                else return "Ваш ИМТ($BMI) отличается от нормального. Нормальный: " +
                        "[${firstValue} - ${secondValue}]"

            }
        }
        return "Ваш ИМТ: ${BMI}"
    }

    private fun createNormalValueForBMI() : HashMap<Pair<Int, Int>, Pair<Double, Double>> {
        val bmiTable = hashMapOf(
            Pair(2, 5) to Pair(14.0, 18.0),
            Pair(6, 12) to Pair(15.0, 21.0),
            Pair(13, 18) to Pair(18.5, 24.9),
            Pair(19, 24) to Pair(19.0, 24.9),
            Pair(25, 34) to Pair(20.0, 25.9),
            Pair(35, 44) to Pair(21.0, 26.9),
            Pair(45, 54) to Pair(22.0, 27.9),
            Pair(55, 64) to Pair(23.0, 28.9),
            Pair(65, 150) to Pair(24.0, 29.9)
        )

        return bmiTable

    }
}