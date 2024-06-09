package com.dain.comp3025_200535561_dainshin_calculator2

import android.util.Log
import com.dain.comp3025_200535561_dainshin_calculator2.databinding.ActivityMainBinding

class Calculator(dataBinding: ActivityMainBinding)
{
    private var binding: ActivityMainBinding = dataBinding
    private var result: String

    init {
        result = ""
        createButtonReferences()
    }

    private fun createButtonReferences()
    {
        val operandButtons = arrayOf(
            binding.oneButton, binding.twoButton, binding.threeButton, binding.fourButton,
            binding.fiveButton, binding.sixButton, binding.sevenButton, binding.eightButton,
            binding.nineButton, binding.zeroButton, binding.plusMinusButton, binding.decimalButton,
            binding.deleteButton
        )

        val operatorButtons = arrayOf(
            binding.minusButton, binding.plusButton, binding.multiplyButton, binding.divideButton,
            binding.percentButton, binding.clearButton
        )

        operandButtons.forEach { it.setOnClickListener { operandHandler(it.tag as String) } }

        operatorButtons.forEach { it.setOnClickListener { operatorHandler(it.tag as String) } }
    }

    private fun operandHandler(tag: String) {
        when(tag)
        {
            "." -> {
                if(!binding.resultTextView.text.contains("."))
                {
                    result += if(result.isEmpty()) "0." else "."

                    binding.resultTextView.text = result
                }
            }
            "delete" -> {
                result = result.dropLast(1)

                binding.resultTextView.text = if(result.isEmpty() || result=="-") "0" else result
            }
            "plus_minus" -> {
                if (result.isNotEmpty() && result != "0")
                {
                    if (result.startsWith("-"))
                    {
                        result =  result.substring(1)
                    }
                    else
                    {
                       result = "-".plus(result)
                    }
                }
                binding.resultTextView.text = result.ifEmpty { "0" }
            }
            else -> {

                if(binding.resultTextView.text == "0")
                {
                    result = tag
                }
                else
                {
                    result += tag
                }
                binding.resultTextView.text = result
            }
        }
    }

    private fun operatorHandler(tag: String)
    {
        when (tag)
        {
            "clear" -> clear()
        }
    }

    private fun clear()
    {
        result = ""
        binding.resultTextView.text = "0"
    }
}


/*
*
b. Write code in the Kotlin programming language that enables the backspace button to
delete the last number entered. If all the numbers have been deleted, then they will be
replaced by a zero. You will also have to create event handlers for the backspace Button
control in the MainActivity (4 Marks Functionality). --> ok

c. Write code in the Kotlin programming language that enables the plus/minus button to
toggle the sign of the number displayed in the Result Label to plus or minus. The
plus/minus button will have no effect on the number zero. You will also have to create
event handler for the plus/minus Button control in the MainActivity (4 Marks:
Functionality) --> ok (마지막 숫자 delete -> 0) 확인하기

d. Write code in the Kotlin programming language that enables the Operator Buttons
(+, -, *, /, %) to work with the operands entered through the number buttons, the plus/minus
button and the decimal point.
When the Equals Button (=) is selected by the user, it performs the calculation between the operands and the operator selected.
For example,
in the expression 1 + 2 = 3 the left most operand is ‘1’ the operator is the ‘+’ and the
right most operand is ‘2’. The value ‘3’ to the right of the equal symbol is the result of
the ‘+’ operation. We can also see this as the addition function taking two parameters
and returning the result. Write one or more evaluation functions to create this logic.
(22 Marks: Functionality).

e. Your evaluation functions must include logic for compound calculations in a series. For
example, a user could select “1 + 2 / 3.5 * 5 =” in a series which will return “3.85714285”.
Your function must respect order of operations (AKA operator precedence or BEDMAS).
Important Note: you may not use a built-in evaluation function, library or framework or
code from the internet for this functionality (15 Marks: Functionality).

f. Floating point numbers should be accurate to at least 8 decimal places. Integers should
be represented as integers. This means if your calculation includes both floating point
numbers and integers and the result is an integer your function should be flexible
enough to remove any extraneous decimal precision (e.g. if your expression is 1.2 - 2.2
this should evaluate to a value of -1 not -1.0). (5 Marks: Functionality).

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
b. Ensure you include a comment header for each of your methods and classes (1 Marks:
Internal Documentation)
c. Ensure your program uses contextual variable names that help make the program
human-readable (1 Marks: Internal Documentation).
d. Ensure you include inline comments that describe your code’s functionality only where
required (1 Marks: Internal Documentation)
* */