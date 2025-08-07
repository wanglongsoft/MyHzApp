package com.example.myhzapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
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

    fun startFlutterView(view: View) {
        val flutterClass = FlutterViewActivity::class.java
        val flutterIntent = Intent(this, flutterClass)
        startActivity(flutterIntent)
    }

    fun startNativeView(view: View) {
        val nativeClass = SecondActivity::class.java
        val flutterIntent = Intent(this, nativeClass)
        startActivity(flutterIntent)
    }
}