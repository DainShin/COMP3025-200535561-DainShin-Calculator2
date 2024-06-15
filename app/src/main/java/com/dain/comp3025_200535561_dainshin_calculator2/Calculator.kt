package com.dain.comp3025_200535561_dainshin_calculator2

import android.util.Log
import com.dain.comp3025_200535561_dainshin_calculator2.databinding.ActivityMainBinding
import java.util.Stack

class Calculator(dataBinding: ActivityMainBinding) {
    val binding = dataBinding
    var isDecimalClicked = false
    var isPlus = true

    var resultStack = Stack<String>()  // each value will be stored in this stack
    var stack = Stack<String>()   // the value  before an operator button is clicked

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
            //binding.percentButton
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
            stack.push(resultFloat.toString())

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
                        resultString = resultVal.toString()
                    }
                } else {
                    var resultVal = (resultStack[i - 1].toFloat() / resultStack[i + 1].toFloat())

                    if (resultVal % 1.0 == 0.0) {
                        resultString = (resultVal.toInt()).toString()
                    } else {
                        resultString = resultVal.toString()
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
                        resultVal.toString()
                    }
                } else {
                    val resultVal = resultStack[j - 1].toFloat() - resultStack[j + 1].toFloat()
                    resultString = if (resultVal % 1.0 == 0.0) {
                        resultVal.toInt().toString()
                    } else {
                        resultVal.toString()
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


/*
b. Write code in the Kotlin programming language that enables the backspace button to
delete the last number entered. If all the numbers have been deleted, then they will be
replaced by a zero. You will also have to create event handlers for the backspace Button
control in the MainActivity (4 Marks Functionality). --> ok

c. Write code in the Kotlin programming language that enables the plus/minus button to
toggle the sign of the number displayed in the Result Label to plus or minus. The
plus/minus button will have no effect on the number zero. You will also have to create
event handler for the plus/minus Button control in the MainActivity (4 Marks:
Functionality) --> ok (마지막 숫자 delete -> 0)

d. Write code in the Kotlin programming language that enables the Operator Buttons
(+, -, *, /, %) to work with the operands entered through the number buttons, the plus/minus
button and the decimal point.
When the Equals Button (=) is selected by the user, it performs the calculation between the operands and the operator selected.
For example,
in the expression 1 + 2 = 3 the left most operand is ‘1’ the operator is the ‘+’ and the
right most operand is ‘2’. The value ‘3’ to the right of the equal symbol is the result of
the ‘+’ operation. We can also see this as the addition function taking two parameters
and returning the result. Write one or more evaluation functions to create this logic.
(22 Marks: Functionality). --> ok

e. Your evaluation functions must include logic for compound calculations in a series.
For example, a user could select “1 + 2 / 3.5 * 5 =” in a series which will return “3.85714285”.
Your function must respect order of operations (AKA operator precedence or BEDMAS).
Important Note: you may not use a built-in evaluation function, library or framework or
code from the internet for this functionality (15 Marks: Functionality). --> ok

f. Floating point numbers should be accurate to at least 8 decimal places. Integers should
be represented as integers. This means if your calculation includes both floating point
numbers and integers and the result is an integer your function should be flexible
enough to remove any extraneous decimal precision (e.g. if your expression is 1.2 - 2.2
this should evaluate to a value of -1 not -1.0). (5 Marks: Functionality). --> ok

g. A Result Label or other Suitable UI Control displays the results of any calculation the
user desires using the Number Buttons, Decimal point button and Operator Buttons.
Your UI should limit the text entry to an appropriate value and not change the Text Size
to suit a large operand or a result. (4 Marks: Functionality).

h. Write code in the Kotlin Programming language that enables the Clear Button to Reset
the Result Label to Zero. You will also have to create an event handler for the Clear
Button control in the MainActivity (4 Marks: Functionality).  --> ok



2. Include Internal Documentation for your program (6 Marks: Internal Documentation):
a. Ensure you include a comment header for your MainActivity file that indicates: the
File name, Author's name, StudentID, Date, App description, and Version information
(3 Marks: Internal Documentation).
*
b. Ensure you include a comment header for each of your methods and classes (1 Marks:
Internal Documentation)

c. Ensure your program uses contextual variable names that help make the program
human-readable (1 Marks: Internal Documentation).

d. Ensure you include inline comments that describe your code’s functionality only where
required (1 Marks: Internal Documentation)
* */


/*

// 스택들...
var stack = Stack<String>()
var resultStack = Stack<String>()

// 만약 현재 스택의 로직이 완성이 되면 resultStack으로 push




* */