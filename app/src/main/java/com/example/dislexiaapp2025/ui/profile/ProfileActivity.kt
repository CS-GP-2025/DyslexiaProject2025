package com.example.dislexiaapp2025.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dislexiaapp2025.databinding.ActivityProfileBinding
import com.example.dislexiaapp2025.repo.local.SharedPref
import com.example.dislexiaapp2025.ui.profile.children.AddChildActivity
import com.example.dislexiaapp2025.ui.profile.children.Children
import com.example.dislexiaapp2025.ui.profile.children.ChildrenTests


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        sharedPreferences = SharedPref(this)


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setControllers()
        setContentView(binding.root)
        setView()
    }
    private fun setControllers(){
        binding.myAccountLL.setOnClickListener {
            animateBtn(binding.myAccountLL
            ) {
                startActivity(Intent(this, EditProfileActivity::class.java))
            }
        }
        binding.addChildLL.setOnClickListener {
            animateBtn(binding.addChildLL
            ) { val intent = Intent(this, AddChildActivity::class.java)
                intent.putExtra("mode", "add")
                startActivity(intent)
            }
        }
        binding.childrenLL.setOnClickListener {
            animateBtn(binding.childrenLL){
            startActivity(Intent(this, Children::class.java))
            }

        }
        binding.logoutLL.setOnClickListener {
        }
        binding.testsLL.setOnClickListener {
            animateBtn(binding.testsLL){
                startActivity(Intent(this, ChildrenTests::class.java))
            }
        }
        binding.backArrowIV.setOnClickListener{
            finish()
        }
    }
    private fun  setView(){

    }

    private fun loadProfileImage() {
        val savedUri = sharedPreferences.getImage()
        if (savedUri != null) {
            val uri = Uri.parse(savedUri)
            binding.profileImage.setImageURI(uri)
        }}

    override fun onResume() {
        super.onResume()
        loadProfileImage()
        val user=sharedPreferences.getProfileDetails()
        binding.userName.text=user.getName()
    }
    private fun animateBtn(b: View,unit:()->Unit){
        b.animate().scaleY(1.2f).scaleX(1.2f).setDuration(150).withEndAction{
            b.animate().scaleX(1f).scaleY(1f).setDuration(150).withEndAction{
               unit()
            }
        }.start()

    }

}