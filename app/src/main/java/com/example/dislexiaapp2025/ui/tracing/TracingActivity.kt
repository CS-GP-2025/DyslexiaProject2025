package com.example.dislexiaapp2025.ui.tracing
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dislexiaapp2025.databinding.ActivityTracingBinding
import com.example.dislexiaapp2025.repo.local.LettersAndNumbers

class TracingActivity : AppCompatActivity() {
    private var index=0
    private var type=""
    private var pathsOfNumber=0
    private var ofCompletedPaths=0
    private lateinit var fragment:TracingFragment
    private lateinit var binding: ActivityTracingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTracingBinding.inflate(layoutInflater)

         index=intent.extras?.getInt("letterIndex",0)!!
         type=intent.extras?.getString("type",null)!!
         fragment= TracingFragment().getInstance(index,type)
        setFrame(fragment)


        binding.doneButton.setOnClickListener{
            pathsOfNumber=fragment.getResult().first
            ofCompletedPaths=fragment.getResult().second
            if(pathsOfNumber==ofCompletedPaths){
                binding.celeprationView.visibility=VISIBLE
                binding.celeprationAnim.playAnimation()
                binding.doneButton.isEnabled=false
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Continue", Toast.LENGTH_SHORT).show()
            }
        }

        binding.nextBtn.setOnClickListener {
            if(type=="letter"){
                if(index!= LettersAndNumbers.letters.size-1){
                    //go to next letter
                    setNextData(index +1,"letter")
                }
                else
                {
                    this.finish()
                }
            }
            else{
                if(index!= LettersAndNumbers.numbers.size-1){
                    //go to next number
                        setNextData(index+1,"number")

                }
                else
                {
                    this.finish()
                }
            }

        }
        binding.backArrowIV.setOnClickListener {
            this.finish()
        }
        setContentView(binding.root)

}
    private fun setNextData(index:Int, type:String){
        this.index=index
        this.type=type
        binding.nextBtn.isEnabled=false
        binding.nextBtn.postDelayed({
            binding.nextBtn.isEnabled=true}
            ,1000)
        binding.celeprationView.visibility= GONE
        binding.doneButton.isEnabled=true
        fragment=TracingFragment().getInstance(index,type)
        setFrame(fragment)
    }
    private fun setFrame(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.frameDrawer.id,fragment)
            .commit()
    }
}