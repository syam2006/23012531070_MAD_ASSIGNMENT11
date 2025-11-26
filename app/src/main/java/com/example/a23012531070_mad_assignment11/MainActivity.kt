package com.example.bmicalculator // Check that this matches your package name

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Initialize Views
        val weightInput = findViewById<EditText>(R.id.etWeight)
        val heightInput = findViewById<EditText>(R.id.etHeight)
        val calculateButton = findViewById<Button>(R.id.btnCalculate)
        val resultText = findViewById<TextView>(R.id.tvResult)

        // 2. Set Button Click Listener
        calculateButton.setOnClickListener {
            val weightStr = weightInput.text.toString()
            val heightStr = heightInput.text.toString()

            // Check if inputs are not empty to prevent app crash
            if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {

                val weight = weightStr.toDouble()
                val height = heightStr.toDouble()

                // Validate that height is not zero to avoid division by zero
                if (height > 0) {
                    val bmi = weight / (height * height)

                    // Display Result formatted to 2 decimal places
                    val bmiResult = String.format("%.2f", bmi)
                    val category = getBMICategory(bmi)

                    resultText.text = "BMI: $bmiResult\n$category"

                    // Optional: Change color based on health
                    updateColor(resultText, bmi)
                } else {
                    Toast.makeText(this, "Height must be greater than 0", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter both weight and height", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Helper function to get category
    private fun getBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal Weight"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }
    }

    // Helper function to change text color visually
    private fun updateColor(view: TextView, bmi: Double) {
        val color = when {
            bmi < 18.5 || bmi >= 30.0 -> android.R.color.holo_red_dark // Warning
            bmi < 25.0 -> android.R.color.holo_green_dark // Healthy
            else -> android.R.color.holo_orange_dark // Warning
        }
        view.setTextColor(ContextCompat.getColor(this, color))
    }
}