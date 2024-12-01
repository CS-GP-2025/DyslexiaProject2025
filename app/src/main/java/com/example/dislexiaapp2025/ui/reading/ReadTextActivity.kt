package com.example.dislexiaapp2025.ui.reading

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dislexiaapp2025.R
import com.example.dislexiaapp2025.databinding.ActivityReadTextBinding
import java.util.Locale

class ReadTextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadTextBinding
    private lateinit var pulseAnimator: ObjectAnimator
    private lateinit var speechRecognizer: SpeechRecognizer
    private var actualText = ""
    private var speechText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadTextBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        checkPermission()
// Check if SpeechRecognizer is supported
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition not supported", Toast.LENGTH_LONG).show()
            return
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        // Set Recognition Listener
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(this@ReadTextActivity, "Listening...", Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {
                //animate the record button
                //and the hint card and the record signals
                binding.recordBtn.animate().scaleY(1.2f).scaleX(1.2f).setDuration(100).withEndAction {
                    binding.recordBtn.setBackgroundResource(R.drawable.is_recording_bg)
                }.start()
                binding.hint.animate().alpha(0f).setDuration(200).withEndAction {
                    binding.hint.visibility=GONE
                    binding.recordSignals.visibility=VISIBLE
                    binding.hintCard.alpha=0f
                    binding.recordSignals.alpha=0f
                    binding.hintCard.animate().alpha(1f).setDuration(200).withEndAction {
                        binding.recordSignals.animate().alpha(1f).setDuration(150).withEndAction {
                            binding.recordSignals.animate().scaleY(0.1f).setDuration(150).withEndAction {
                                binding.recordSignals.animate().scaleY(1f).setDuration(300)
                            }.start()

                        }.start()
                    }.start()
                }.start()
            }
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                Toast.makeText(this@ReadTextActivity, "Stopped Listening", Toast.LENGTH_SHORT)
                    .show()
                binding.recordBtn.animate().scaleY(1f).scaleX(1f).setDuration(100).withEndAction {
                    binding.recordBtn.setBackgroundResource(R.drawable.is_not_recording)}.start()
                pulseAnimator.cancel()
            }

            @SuppressLint("SetTextI18n")
            override fun onError(error: Int) {
                Toast.makeText(this@ReadTextActivity, "Try again", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
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


        // Set click listener for the record button
        binding.recordBtn.setOnClickListener{
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something")
            }
            speechRecognizer.startListening(intent)
            setupPulseAnimation()
            pulseAnimator.start()
        }


        binding.backArrowIV.setOnClickListener{
            this.finish()
        }


        setContentView(binding.root)
}
    // check for the permission
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
    }
    //animation for the record signals
    private fun setupPulseAnimation() {
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
    //go to the result activity
    fun goToResult(){
        val intent = Intent(this, ReadingResult::class.java)
        actualText = binding.actualText.text.toString()
        intent.putExtra("actualText",actualText)
        intent.putExtra("speechText",speechText)
        startActivity(intent)
    }
    //destroy the speech recognizer
    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }
}
