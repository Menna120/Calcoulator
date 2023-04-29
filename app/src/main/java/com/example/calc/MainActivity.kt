package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.text.isDigitsOnly
import com.example.calc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var currentText: String
    private var operation: Boolean = false
    private var tempResult: Double = 0.0
    private lateinit var resultText: String
    private var result: Double = 0.0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addCallbacks()
    }

    private fun addCallbacks() {
        binding.buttonBack.setOnClickListener { returnBack() }
        binding.buttonBack.setOnLongClickListener{ clear() }
        binding.buttonClear.setOnClickListener { clearText() }
        binding.buttonEqual.setOnClickListener {
            updateOperation()
            currentText = tempResult.toString()
            binding.textViewInput.text = currentText
            resultText = "0"
            binding.textViewResult.text = resultText
        }
        binding.buttonMul.setOnClickListener {
            it.displayInputText()
            operation = true
        }
        binding.buttonPlus.setOnClickListener {
            it.displayInputText()
            operation = true
        }
        binding.buttonMinus.setOnClickListener {
            it.displayInputText()
            operation = true
        }
        binding.buttonDiv.setOnClickListener {
            it.displayInputText()
            operation = true
        }
        binding.buttonPow.setOnClickListener {
            it.displayInputText()
            operation = true
        }
    }

    private fun updateOperation() {
        if (operation) {
            tempResult = result
        }
    }

    private fun value(view: View): String {
        return (view as Button).text.toString()
    }

    private fun View.displayInputText() {
        currentText = binding.textViewInput.text.toString() + value(this)
        binding.textViewInput.text = currentText
    }

    private fun displayOutputText() {
        result = calculator(currentText)
        resultText = result.toString()
        binding.textViewResult.text = resultText
    }

    fun click(view: View) {
        view.displayInputText()
        displayOutputText()
    }

    private fun clear(): Boolean {
        currentText = ""
        resultText = "0"
        binding.textViewInput.text = currentText
        binding.textViewResult.text = resultText
        return true
    }

    private fun clearText() {
        operation = true
        result = 0.0
        clear()
    }

    private fun returnBack() {
        operation = false
        if (currentText.length > 1) {
            currentText = currentText.dropLast(1)
            binding.textViewInput.text = currentText
            result = if (currentText.isDigitsOnly()) {
                currentText.toDouble()
            } else {
                calculator(currentText)
            }
            binding.textViewResult.text = result.toString()
        } else {
            clear()
        }
    }
}
