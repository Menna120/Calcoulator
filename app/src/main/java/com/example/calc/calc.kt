package com.example.calc

import java.util.*

fun calcoulator(infixExp: String): Double {
    // find precedence of operators.
    val precedence = mapOf('^' to 4, '*' to 3, '/' to 3, '-' to 2, '+' to 2)

    // stack to store operands.
    val operandStack = Stack<Double>()

    // stack to store operators.
    val operatorStack = Stack<Char>()

    var exp:String=infixExp

    if(!exp.last().isDigit())
        exp=exp.dropLastWhile { !it.isDigit() }

    // Current token is either an opening brace or an exponent symbol, push it to operator stack
    var idx = 0
    while (idx < exp.length) {
        val token = infixExp[idx]

        when {
            token.isDigit() || (token == '-' && idx + 1 < infixExp.length && infixExp[idx + 1].isDigit()) -> {
                // Extract the entire number from the input string
                var numStr = ""
                if (token == '-') {
                    numStr += '-'
                    idx++
                }
                while (idx < infixExp.length && (infixExp[idx].isDigit() || infixExp[idx] == '.')) {
                    numStr += infixExp[idx]
                    idx++
                }
                operandStack.push(numStr.toDouble())
                continue
            }

            token in "+-*/^" -> {
                while (!operatorStack.isEmpty() && precedence[operatorStack.peek()]!! >= precedence[token]!! &&
                    (precedence[operatorStack.peek()]!! != 4 || (precedence[operatorStack.peek()]!! == 4 && idx > 1))) {
                    val op = operatorStack.pop()
                    val op2 = operandStack.pop()
                    val op1 = operandStack.pop()
                    operandStack.push(doMath(op, op1, op2))
                }
                operatorStack.push(token)
                idx++
                continue
            }
        }

        idx++
    }

    // Entire expression has been parsed
    // at this point, apply remaining operators
    // to remaining operands.
    while (!operatorStack.isEmpty()) {
        val op = operatorStack.pop()
        val op2 = operandStack.pop()
        val op1 = operandStack.pop()
        operandStack.push(doMath(op, op1, op2))
    }

    // Top of operand stack contains result,
    // return it and clear the stack.
    return operandStack.pop()
}

// Function that returns value of
// expression after evaluation.
fun doMath(operator: Char, op1: Double, op2: Double): Double {
    return when (operator) {
        '+' -> op1 + op2
        '-' -> op1 - op2
        '*' -> op1 * op2
        '/' -> op1 / op2
        '^' -> Math.pow(op1, op2)
        else -> 0.0
    }
}