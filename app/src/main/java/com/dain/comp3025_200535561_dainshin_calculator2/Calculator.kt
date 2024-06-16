package com.dain.comp3025_200535561_dainshin_calculator2

import android.util.Log
import com.dain.comp3025_200535561_dainshin_calculator2.databinding.ActivityMainBinding
import java.util.Stack
import java.text.DecimalFormat

class Calculator(dataBinding: ActivityMainBinding) {
    val binding = dataBinding
    var isDecimalClicked = false
    var isPlus = true

    var resultStack = Stack<String>()  // each value will be stored in this stack
    var stack = Stack<String>()   // the value  before an operator button is clicked

    val eightDecimal = DecimalFormat("#.########")

    init {
        createButtonReferences()
    }

    private fun createButtonReferences() {
        val operandButtons = arrayOf(
            binding.plusMinusButton, binding.deleteButton, binding.clearButton
        )

        val numberButtons = arrayOf(
            binding.oneButton,
            binding.twoButton,
            binding.threeButton,
            binding.fourButton,
            binding.fiveButton,
            binding.sixButton,
            binding.sevenButton,
            binding.eightButton,
            binding.nineButton,
            binding.zeroButton,
            binding.decimalButton
        )

        val operatorButtons = arrayOf(
            binding.plusButton,
            binding.minusButton,
            binding.divideButton,
            binding.multiplyButton,

        )

        val equalsButton = binding.equalsButton
        val percentButton = binding.percentButton

        operandButtons.forEach { it.setOnClickListener { operandHandler(it.tag.toString()) } }
        numberButtons.forEach { it.setOnClickListener { numberHandler(it.tag.toString()) } }
        operatorButtons.forEach { it.setOnClickListener { operatorHandler(it.tag.toString()) } }
        equalsButton.setOnClickListener { equalsHandler() }
        percentButton.setOnClickListener { percentHandler() }

    }

    private fun percentHandler() {
        if(stack.isNotEmpty()) {

            val resultString = stack.joinToString("")
            val resultFloat = resultString.toFloat() * 0.01

            stack.clear()
            stack.push(eightDecimal.format(resultFloat).toString())

            updateResultView()
            updateProcessView()
        }
    }


    // if equals button is clicked
    private fun equalsHandler() {

        if (stack.isNotEmpty()) {
            val resultString = stack.joinToString("")
            resultStack.push(resultString)
            // empty stack
            isDecimalClicked = false
            stack.clear()
        }

        //  * or /
        var i = 0
        while (i < resultStack.size) {
            val operator = resultStack[i]
            if (operator == "*" || operator == "/") {
                var resultString = ""

                if (operator == "*") {
                    var resultVal = (resultStack[i - 1].toFloat() * resultStack[i + 1].toFloat())

                    if (resultVal % 1.0 == 0.0) {
                        resultString = (resultVal.toInt()).toString()
                    } else {
                        resultString = eightDecimal.format(resultVal).toString()
                    }
                } else {
                    var resultVal = (resultStack[i - 1].toFloat() / resultStack[i + 1].toFloat())
                    resultVal =  String.format("%.8f", resultVal).toFloat()

                    if (resultVal % 1.0 == 0.0) {
                        resultString = (resultVal.toInt()).toString()
                    } else {
                        resultString = eightDecimal.format(resultVal).toString()
                    }
                }

                resultStack[i - 1] = resultString
                resultStack.removeAt(i)
                resultStack.removeAt(i)

                i--
            } else {
                i++
            }
        }

        // + or -
        var j = 0
        while (j < resultStack.size) {
            val operator = resultStack[j]
            if (operator == "+" || operator == "-") {
                var resultString = ""

                if (operator == "+") {
                    val resultVal = resultStack[j - 1].toFloat() + resultStack[j + 1].toFloat()
                    resultString = if (resultVal % 1.0 == 0.0) {
                        resultVal.toInt().toString()
                    } else {
                        eightDecimal.format(resultVal).toString()
                    }
                } else {
                    val resultVal = resultStack[j - 1].toFloat() - resultStack[j + 1].toFloat()
                    resultString = if (resultVal % 1.0 == 0.0) {
                        resultVal.toInt().toString()
                    } else {
                        eightDecimal.format(resultVal).toString()
                    }
                }

                resultStack[j - 1] = resultString
                resultStack.removeAt(j)
                resultStack.removeAt(j)

                j--
            } else {
                j++
            }
        }

        stack.clear()
        var result = resultStack.peek()
        binding.resultTextView.text = result
        stack.push(result)
        resultStack.clear()
        updateProcessView()

    }

    // numbers and operators will be in the stack
    private fun operatorHandler(operator: String) {
        // enter the number and press the operator
        // -> copy stack value to resultStack
        // -> empty stack

        // copy stack value to resultStack
        if (stack.isNotEmpty()) {
            val resultString = stack.joinToString("")
            resultStack.push(resultString)
            // empty stack
            isDecimalClicked = false
            stack.clear()
        }


        // if the previous value is an operator, call pop() and store the new operator
        if (resultStack.peek() == "+" || resultStack.peek() == "-" || resultStack.peek() == "*" || resultStack.peek() == "/") {
            resultStack.pop()
        }

        when (operator) {
            "plus" -> resultStack.push("+")
            "minus" -> resultStack.push("-")
            "multiply" -> resultStack.push("*")
            "divide" -> resultStack.push("/")
        }
        updateResultView()
        updateProcessView()
    }

    private fun numberHandler(num: String) {
        if(stack.size < 11) {
            if (num == "." && isDecimalClicked) {
                return
            }

            if (stack.isEmpty()) {
                if (num == ".") {
                    stack.push("0.")
                    isDecimalClicked = true
                } else {
                    stack.push(num)
                }
            } else if (stack.size == 1 && stack.peek() == "0" && num != "0" && num != ".") {
                stack.pop()
                stack.push(num)
            } else {
                if (num == "." && !isDecimalClicked) {
                    stack.push(num)
                    isDecimalClicked = true
                } else if (num != "." || (num == "." && !stack.peek().contains("."))) {
                    stack.push(num)
                }
            }
        }

        updateResultView()
        updateProcessView()
    }


    private fun operandHandler(tag: String) {
        when (tag) {
            "delete" -> {
                if (binding.resultTextView.text == "0") {
                    isPlus = true
                }
                if (stack.peek() == ".") {
                    isDecimalClicked = false
                }
                stack.pop()
            }

            "clear" -> {
                stack.clear()
                resultStack.clear()
                isDecimalClicked = false
                isPlus = true
            }

            "plus_minus" -> {
                isPlus = !isPlus

                if (stack.isNotEmpty()) {
                    val stringValue = stack.joinToString("")
                    if (isPlus == false) {
                        stack.clear()
                        stack.push("-$stringValue")
                    } else {
                        stack.clear()
                        stack.push(stringValue)
                    }
                }

            }
        }
        updateResultView()
        updateProcessView()
    }

    // update result view screen
    private fun updateResultView() {
        if (stack.isEmpty()) {
            binding.resultTextView.text = "0"
            isPlus = true
            isDecimalClicked = false
        } else {
            binding.resultTextView.text = stack.joinToString("")
        }
    }

    private fun updateProcessView() {
        binding.processTextView.text = resultStack.joinToString("")
    }

}


