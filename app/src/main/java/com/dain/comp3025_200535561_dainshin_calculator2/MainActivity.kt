package com.dain.comp3025_200535561_dainshin_calculator2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dain.comp3025_200535561_dainshin_calculator2.databinding.ActivityMainBinding

/**
 * File name: 200535561_DainShin_Calculator
 * Author's name: Dain Shin
 * Student ID: 200535561
 * Date: May 31st, 2024
 * App Description:
 * This is a basic calculator capable of performing four arithmetic operations. Four arithmetic operations on decimal points, percentages, positive and negative numbers are possible.
 * Version: Ver1.0
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create a reference to the ActivityMainBinding Class object
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calculator = Calculator(binding)

    }
}