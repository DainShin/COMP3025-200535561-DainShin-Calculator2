package com.dain.comp3025_200535561_dainshin_calculator2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}