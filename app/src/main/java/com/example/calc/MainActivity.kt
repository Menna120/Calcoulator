package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.text.isDigitsOnly
import com.example.calc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var currenttext:String
    var operation:Boolean=false
    private var tempresult:Double=0.0
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

        binding.buttonBack.setOnLongClickListener{clear()}

        binding.buttonClear.setOnClickListener { cleartext() }

        binding.buttonEqual.setOnClickListener{
            updateOperation()
            currenttext=tempresult.toString()
            binding.textViewInput.text=currenttext
            resulttext="0"
            binding.textViewResult.text=resulttext
            }

        binding.buttonMul.setOnClickListener{it.displayInputText()
            operation=true}

        binding.buttonPlus.setOnClickListener{it.displayInputText()
            operation=true}

        binding.buttonMinus.setOnClickListener{it.displayInputText()
            operation=true }

        binding.buttonDiv.setOnClickListener{it.displayInputText()
            operation=true}

        binding.buttonPow.setOnClickListener{it.displayInputText()
            operation=true}
    }
    private fun updateOperation(){
        if(operation)
            tempresult=result
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
    private fun clear():Boolean{
        currenttext=""
        resulttext="0"
        binding.textViewInput.text=currenttext
        binding.textViewResult.text=resulttext
        return true
    }
    private fun cleartext() {
        operation=true
        result=0.0
        clear()
    }
    private fun returnback() {
        operation=false
        if(currenttext.length>1) {
            currenttext = currenttext.dropLast(1)
            binding.textViewInput.text = currenttext
            result = if(currenttext.isDigitsOnly())
                        currenttext.toDouble()
                    else
                        calcoulator(currenttext)
            binding.textViewResult.text = result.toString()
            }
        else
            {
                clear()
            }
    }
}