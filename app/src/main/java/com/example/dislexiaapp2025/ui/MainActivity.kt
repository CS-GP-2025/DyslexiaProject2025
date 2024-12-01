package com.example.dislexiaapp2025.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dislexiaapp2025.databinding.ActivityMainBinding
import com.example.dislexiaapp2025.repo.local.SharedPref
import com.example.dislexiaapp2025.ui.reading.ReadingMainActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        pref = SharedPref(this)


        binding.letters.setOnClickListener {
            numbersOrLetters(result = "letters")
        }
        binding.numbers.setOnClickListener {
            numbersOrLetters(result = "numbers")
        }
        binding.hideView.setOnClickListener{
            binding.beginnerOrProf.visibility= GONE
        }
        binding.readingBtn.setOnClickListener {
            val intent = Intent(this, ReadingMainActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)

    }
    private fun numbersOrLetters(result: String){
        binding.beginnerOrProf.visibility=VISIBLE
        binding.beginnerIV.setOnClickListener {
            pref.setMode("beginner")
            sendResult(result)
        }
        binding.proIV.setOnClickListener {
            pref.setMode("professional")
            sendResult(result)
        }
    }
    private fun sendResult(result: String) {
        val intent = Intent(this, LettersNumbersActivity::class.java)
        intent.putExtra("result", result)
        startActivity(intent)
        binding.beginnerOrProf.visibility=GONE
    }
}