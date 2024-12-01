package com.example.dislexiaapp2025.ui.drawing

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.dislexiaapp2025.R
import com.example.dislexiaapp2025.databinding.ActivityDrawingBinding
import com.example.dislexiaapp2025.repo.local.LettersAndNumbers

class DrawingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawingBinding
    private lateinit var fragment: DrawingFragment
    var index=0
    var type=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDrawingBinding.inflate(layoutInflater)
        //get the data from the previos
        index=intent.extras?.getInt("letterIndex",0)!!
        type=intent.extras?.getString("type",null)!!
        fragment = DrawingFragment().getInstance(index,type)
        setFrame(fragment)
        binding.letterName.text=setNameOfLetter(type,index)

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
    private fun setNameOfLetter(type:String,index:Int):String{
        var name=""
        if(type=="letter"){
            name= LettersAndNumbers.letters[index].name
        }else{
            name= LettersAndNumbers.numbers[index].name
        }
        return name
    }
}