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
import com.example.dislexiaapp2025.util.adapter.TextsAdapter
import com.example.dislexiaapp2025.util.listener.TextListener
import java.util.Locale


class ReadTextActivity : AppCompatActivity(),TextListener {
    private lateinit var binding: ActivityReadTextBinding
    private lateinit var pulseAnimator: ObjectAnimator
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var adapter :TextsAdapter
    private var actualText = ""
    private var speechText = ""
    private var isRecording = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadTextBinding.inflate(layoutInflater)
        enableEdgeToEdge()

        //prepare texts
        val listToChoose = arrayListOf(
            "Hello how are you",
            "I am fine thank you",
            "My name is John",
            "I am 25 years old"
           , "I live in New York"
           , "It is sunny and warm today"
           , "I love to read books"
           , "I like to play sports"
           , "My favorite color is blue"
           , "I work 40 hours a week"
           , "The capital of France is Paris"
           , "The largest planet in our solar system is Jupiter"
           , "The currency of Japan is the yen"
           , "The largest country in the world is Russia"
           , "The smallest country in the world is Vatican City"
           , "The capital of Australia is Canberra"
           , "The largest ocean in the world is the Pacific Ocean"
           , "The smallest ocean in the world is the Arctic Ocean"
           , "The largest continent in the world is Asia"
           , "The smallest continent in the world is Antarctica"
           , "The capital of India is New Delhi"
           , "The largest river in the world is the Nile River"
           , "The smallest river in the world is the Amazon River"
           , "The largest desert in the world is the Sahara Desert"
           , "The smallest desert in the world is the Atacama Desert"
           , "The capital of Switzerland is Bern"
           , "The largest mountain in the world is Mount Everest"
           , "The smallest mountain in the world is Mount Kilimanjaro"
           , "The largest lake in the world is Lake Baikal"
           , "The smallest lake in the world is Lake Baikal"
           , "The capital of Canada is Ottawa"
           , "The largest country in the world is Russia"
           , "The smallest country in the world is Vatican City"
           , "The capital of Australia is Canberra"
           , "The largest ocean in the world is the Pacific Ocean"
           , "The smallest ocean in the world is the Arctic Ocean"

        )
        adapter= TextsAdapter(listToChoose,this)
        binding.recyclerView.adapter = adapter














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
                if (isRecording) {
                    speechRecognizer.startListening(intent) // إعادة التشغيل
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onError(error: Int) {
            }


            override fun onResults(results: Bundle?) {
                //when results are returned, get the text and go to result activity
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    speechText=matches[0]
                }
                if(isRecording) {
                    speechRecognizer.startListening(intent)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val partialMatch = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
                if (!partialMatch.isNullOrEmpty()) {
                    Toast.makeText(this@ReadTextActivity, partialMatch, Toast.LENGTH_SHORT).show()  // Show partial results
                }
            }
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }
        )



        // Set click listener for the record button

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something")
            }
        binding.recordBtn.setOnClickListener {
            speechRecognizer.startListening(intent)
            setupPulseAnimation()
            pulseAnimator.start()
            //when record button is clicked, start listening
            isRecording=!isRecording
            if(isRecording) {
                speechRecognizer.startListening(intent)
                setupPulseAnimation()
                //animate the record signals
                pulseAnimator.start()
            }
            else {
                //stop listening
                speechRecognizer.stopListening()
                //when speech ends, stop listening
                Toast.makeText(this@ReadTextActivity, "Stopped Listening", Toast.LENGTH_SHORT)
                    .show()
                //animate the record button
                binding.recordBtn.animate().scaleY(1f).scaleX(1f).setDuration(100).withEndAction {
                    binding.recordBtn.setBackgroundResource(R.drawable.is_not_recording)}.start()
                //stop pulse animation
                pulseAnimator.cancel()
                //go to result activity
                goToResult()

        }
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
    private fun goToResult(){
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

    override fun onClick(text: String) {
        binding.theMainView.visibility = VISIBLE
        binding.actualText.text=text
        binding.dummyView.visibility = GONE
    }
}
