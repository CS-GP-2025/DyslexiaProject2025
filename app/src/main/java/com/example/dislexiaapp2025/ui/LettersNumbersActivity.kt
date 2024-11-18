package com.example.dislexiaapp2025.ui

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dislexiaapp2025.model.Letter
import com.example.dislexiaapp2025.util.adapter.LettersAdapter
import com.example.dislexiaapp2025.repo.local.LettersAndNumbers
import com.example.dislexiaapp2025.databinding.ActivityLettersNumbersBinding
import com.example.dislexiaapp2025.repo.local.SharedPref
import com.example.dislexiaapp2025.ui.drawing.DrawingActivity
import com.example.dislexiaapp2025.ui.tracing.TracingActivity
import com.example.dislexiaapp2025.util.listener.LettersListener

class LettersNumbersActivity : AppCompatActivity() , LettersListener {
    private lateinit var binding: ActivityLettersNumbersBinding
    private lateinit var list: List<Letter>
    private lateinit var title: String
    private lateinit var pref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (intent.extras?.getString("result")=="letters"){
            list= LettersAndNumbers.letters
             title="Letters"
        }else{
            list= LettersAndNumbers.numbers
            title="Numbers"
        }
        binding = ActivityLettersNumbersBinding.inflate(layoutInflater)
        pref = SharedPref(this)
        binding.title.text=title
        val recyclerView=binding.recycler
        val gridLayoutManager = GridLayoutManager(this, 2) // Number of columns
        recyclerView.layoutManager = gridLayoutManager

// Add item spacing decoration to align items properly
       recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                parent.getChildAdapterPosition(view)
                gridLayoutManager.spanCount
                state.itemCount

                // Apply equal padding around items to center-align them
                val spacing = 50 // Adjust as needed
                outRect.left = spacing / 2
                outRect.right = spacing / 2
                outRect.top = spacing / 2
                outRect.bottom = spacing / 2
            }
        })
        recyclerView.adapter = LettersAdapter(list,this)



        binding.backArrowIV.setOnClickListener{
            this.finish()
        }

        setContentView(binding.root)
    }

    override fun OnClick(letter: Letter, position: Int) {
        val intent1 = Intent(this, TracingActivity::class.java)
        intent1.putExtra("letterIndex",position)
        intent1.putExtra("type",letter.type)
        val intent = Intent(this, DrawingActivity::class.java)
        intent.putExtra("letterIndex", position)
        intent.putExtra("type",letter.type)
        if(pref.getMode()=="beginner")
            startActivity(intent1)
        else
            startActivity(intent)
    }
}