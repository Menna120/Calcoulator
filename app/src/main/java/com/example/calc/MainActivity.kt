package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calc.databinding.ActivityMainBinding
import com.example.calculator.operations


class MainActivity : AppCompatActivity() {
    private lateinit var currenttext:String
    lateinit var value:String
    private var currentoperation: operations? = null
    private var previousoperation: operations? = null
    private var temp:String=""
    private lateinit var old:String
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
            prepare(null,it)
            binding.textViewInput.text=old
            binding.textViewResult.text="0"
        }

        binding.buttonMul.setOnClickListener{prepare(operations.Mul,it) }

        binding.buttonPlus.setOnClickListener{prepare(operations.Plus,it)}

        binding.buttonMinus.setOnClickListener{prepare(operations.Minus,it)}

        binding.buttonDiv.setOnClickListener{prepare(operations.Div,it)}

        binding.buttonMod.setOnClickListener{ prepare(operations.Mod,it)}

    }
    private fun prepare(operation:operations?,view: View){
        previousoperation=currentoperation
        temp=""
        old = if(currentoperation==null)
                    binding.textViewInput.text.toString()
              else  binding.textViewResult.text.toString()
        currentoperation= operation
        view.displaytext()

    }

    private fun value(view: View):String{
        return (view as Button).text.toString()
    }
    private fun View.displaytext(){
        currenttext=binding.textViewInput.text.toString()+value(this)
        binding.textViewInput.text=currenttext
    }

    private fun calculations(){
        when(currentoperation){
            operations.Mul -> {
                result=old.toDouble()*temp.toDouble()
                binding.textViewResult.text=result.toString()}
            operations.Div ->{
                result=old.toDouble()/temp.toDouble()
                binding.textViewResult.text=result.toString()}
            operations.Plus -> {
                result=old.toDouble()+temp.toDouble()
                binding.textViewResult.text=result.toString()}
            operations.Minus -> {
                result=old.toDouble()-temp.toDouble()
                binding.textViewResult.text=result.toString()}
            operations.Mod -> {
                result=old.toDouble()%temp.toDouble()
                binding.textViewResult.text=result.toString()}
            null -> {binding.textViewResult.text= currenttext}

        }
    }

    fun click(view: View) {
        value=value(view)
        temp += value
        view.displaytext()
        calculations()
    }

    fun cleartext() {
        binding.textViewInput.text=""
        binding.textViewResult.text="0"
        temp=""
        currentoperation=null
    }

    fun returnback() {
        if (currenttext.length>1) {
            val x = currenttext.last()
            currenttext = currenttext.dropLast(1)
            val y = currenttext.last()
            Log.d("MainActivity", "returnback: $y")
            binding.textViewInput.text = currenttext

            if (x.isDigit() && y.isDigit()) {
                temp = temp.dropLast(1)
                calculations()
            }
            else if (x.isDigit() && !y.isDigit()) {
                temp = temp.dropLast(1)

            }
            else if (!x.isDigit()) {
                temp = currenttext.takeLastWhile { it.isDigit() }
                Log.d("MainActivity", "returnback: $temp")
                currentoperation = previousoperation
                calculations()
            }
        }
        else
            cleartext()

    }
}


