package com.example.dislexiaapp2025.ui.reading

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.dislexiaapp2025.R
import com.example.dislexiaapp2025.databinding.ActivityReadCustomTextBinding
import java.util.Locale

class ReadCustomTextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadCustomTextBinding
    lateinit var pulseAnimator:ObjectAnimator
    private lateinit var speechRecognizer: SpeechRecognizer
    private var actualText = ""
    private var speechText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadCustomTextBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        //style the go button when text field is not empty
        binding.inputField.doOnTextChanged{ text, _, _, _ ->
            if(text != "") {
             binding.goBtn.setBackgroundResource(R.drawable.is_recording_bg)
            }
        }

        //check permission
        checkPermission()
        //check if speech recognition is available
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition not supported", Toast.LENGTH_LONG).show()
            return
        }
        //create speech recognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        //set listener for speech recognizer
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(this@ReadCustomTextActivity, "Listening...", Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {
                //animate the record button
                binding.recordBtn.animate().scaleY(1.2f).scaleX(1.2f).setDuration(100).withEndAction {
                    binding.recordBtn.setBackgroundResource(R.drawable.is_recording_bg)
                }.start()
            }

            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                //when speech ends, stop listening
                Toast.makeText(this@ReadCustomTextActivity, "Stopped Listening", Toast.LENGTH_SHORT)
                    .show()
                //animate the record button
                binding.recordBtn.animate().scaleY(1f).scaleX(1f).setDuration(100).withEndAction {
                    binding.recordBtn.setBackgroundResource(R.drawable.is_not_recording)}.start()
                //stop pulse animation
                pulseAnimator.cancel()
            }

            @SuppressLint("SetTextI18n")
            override fun onError(error: Int) {
                //when error occurs, show error message
                Toast.makeText(this@ReadCustomTextActivity, "Try again", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                //when results are returned, get the text and go to result activity
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    speechText=matches[0]
                    goToResult()
                }
            }


            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }
        )




        binding.goBtn.setOnClickListener {
            //when go button is clicked, check if text field is not empty
            if(binding.inputField.text.toString() != "") {
            binding.readingText.text = binding.inputField.text
            binding.inputField.setText("")
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // Hide the keyboard
            imm.hideSoftInputFromWindow(binding.inputField.windowToken, 0)
                //hide the go button and input field
            binding.goBtn.visibility= GONE
            binding.inputField.visibility = GONE
                //show the record button and record signals
            binding.recordBtn.visibility =VISIBLE
            binding.hintCard.visibility = VISIBLE
            binding.recordSignals.visibility = VISIBLE}

        }

        binding.recordBtn.setOnClickListener {
            //when record button is clicked, start listening
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something")
            }
            speechRecognizer.startListening(intent)
            setupPulseAnimation()
            //animate the record signals
            pulseAnimator.start()
        }
        binding.backArrowIV.setOnClickListener{
            //when back arrow is clicked, go back to previous activity
            finish()
        }
        setContentView(binding.root)
}
    private fun checkPermission() {
        //to check if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
    }
    private fun setupPulseAnimation() {
        //to set up pulse animation
        pulseAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.recordSignals,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.2f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.2f)
        ).apply {
            duration = 500
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
        }
    }
    fun goToResult(){
        // go to result activity
        //with the actual text and speech text
        val intent = Intent(this, ReadingResult::class.java)
        actualText = binding.readingText.text.toString()
        intent.putExtra("actualText",actualText)
        intent.putExtra("speechText",speechText)
        startActivity(intent)
    }
    override fun onDestroy() {
        //when activity is destroyed, destroy the speech recognizer to save your resources
        super.onDestroy()
        speechRecognizer.destroy()
    }
}