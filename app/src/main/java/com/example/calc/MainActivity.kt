package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var currenttext:String
    private lateinit var resulttext:String
    private var result:Double=0.0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addCallBacks()
    }

    private fun addCallBacks() {
        binding.buttonBack.setOnClickListener { returnback() }

        binding.buttonClear.setOnClickListener { cleartext() }

        binding.buttonEqual.setOnClickListener{
            currenttext=result.toString()
            binding.textViewInput.text=currenttext
            resulttext="0"
            binding.textViewResult.text=resulttext
            }

        binding.buttonMul.setOnClickListener{it.displayInputText() }

        binding.buttonPlus.setOnClickListener{it.displayInputText() }

        binding.buttonMinus.setOnClickListener{it.displayInputText() }

        binding.buttonDiv.setOnClickListener{it.displayInputText() }

        binding.buttonMod.setOnClickListener{it.displayInputText() }

    }
    private fun value(view: View):String{
        return (view as Button).text.toString()
    }
    private fun View.displayInputText(){
        currenttext=binding.textViewInput.text.toString()+value(this)
        binding.textViewInput.text=currenttext
    }
    private fun displayOutputText() {
        result =calcoulator(currenttext)
        resulttext=result.toString()
        binding.textViewResult.text=resulttext
    }
    fun click(view: View) {
        view.displayInputText()
        displayOutputText()

    }
    private fun cleartext() {
        result=0.0
        currenttext=""
        resulttext="0"
        binding.textViewInput.text=currenttext
        binding.textViewResult.text=resulttext

    }
    private fun returnback() {
            if(currenttext.length>1) {
                currenttext = currenttext.dropLast(1)
                binding.textViewInput.text = currenttext
                result = if (currenttext != "" && !currenttext.last().isDigit())
                    calcoulator(currenttext.dropLast(1))
                else calcoulator(currenttext)
                binding.textViewResult.text = result.toString()
            }
        else
                cleartext()

    }
}


