package com.example.dislexiaapp2025.ui.math

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dislexiaapp2025.databinding.ActivityMainMathBinding

class MainMathActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMathBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMathBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        var nameOFOperation=""
        var beginnerOrProf=""

        binding.proIV.setOnClickListener{
            beginnerOrProf="pro"
            hideTheView()
            goTOActivity(nameOFOperation,beginnerOrProf)
        }
        binding.beginnerIV.setOnClickListener{
            beginnerOrProf="beginner"
            hideTheView()
            goTOActivity(nameOFOperation,beginnerOrProf)
        }


        binding.AddingBtn.setOnClickListener {
            nameOFOperation="Adding"
            viewBeginnerOrProf()

        }
        binding.subtractingBtn.setOnClickListener {
            nameOFOperation="subtracting"
            viewBeginnerOrProf()

        }
        binding.comparisonBtn.setOnClickListener {
            nameOFOperation="comparison"
            viewBeginnerOrProf()

        }
        binding.arithmeticSeqBtn.setOnClickListener {
            nameOFOperation="arithmeticSeq"
            viewBeginnerOrProf()

        }
        binding.hideView.setOnClickListener {
            hideTheView()

        }
        binding.backArrowIV.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }

     private fun viewBeginnerOrProf() {
         binding.beginnerOrProf.visibility= VISIBLE
         binding.btns.visibility= GONE
         binding.beginnerOrProf.animate().scaleX(0f).scaleY(0f).setDuration(0).start()
         binding.beginnerOrProf.animate().scaleX(1f).scaleY(1f).setDuration(500).start()
     }

    private fun hideTheView(){
        binding.beginnerOrProf.visibility = View.GONE
        binding.btns.visibility = VISIBLE
    }
    private fun goToAddingActivity(name:String, level:String){
        val intent = Intent(this, AddingActivity::class.java)
        intent.putExtra("operationName",name)
        intent.putExtra("operationLevel",level)
        startActivity(intent)
    }
    private fun goToComparisonActivity(level:String){
        val intent = Intent(this, ComparisonActivity::class.java)
        intent.putExtra("level",level)
        startActivity(intent)
    }
    private fun goToArithmeticSeqActivity(level:String){
        val intent = Intent(this, ArithmeticSequenceActivity::class.java)
        intent.putExtra("level",level)
        startActivity(intent)
    }
    private fun goTOActivity(name:String,level:String){
        when(name){
            "Adding","subtracting"->goToAddingActivity(name,level)
            "comparison"->goToComparisonActivity(level)
            "arithmeticSeq"->goToArithmeticSeqActivity(level)
        }
    }

}