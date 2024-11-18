package com.example.dislexiaapp2025.ui.drawing

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.dislexiaapp2025.R
import com.example.dislexiaapp2025.databinding.ActivityDrawingBinding

class DrawingActivity : AppCompatActivity() {
    lateinit var binding: ActivityDrawingBinding
    lateinit var fragment: DrawingFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDrawingBinding.inflate(layoutInflater)
        fragment = DrawingFragment()
        setFrame(fragment)
        binding.backArrowIV.setOnClickListener{
            finish()
        }


        setContentView(binding.root)

    }

    private fun setFrame(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.frameDrawer.id,fragment)
            .commit()
    }
}