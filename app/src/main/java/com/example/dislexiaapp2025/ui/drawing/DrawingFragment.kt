package com.example.dislexiaapp2025.ui.drawing

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.dislexiaapp2025.databinding.FragmentDrawingBinding


class DrawingFragment : Fragment() {
    lateinit var binding: FragmentDrawingBinding
    private var floatStartX = 0f
    private var floatStartY = 0f
    private var floatEndX = 0f
    private var floatEndY = 0f
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private val paint = Paint()
    private var imageView: ImageView? = null


    fun getInstance(index:Int,type:String): DrawingFragment {
        val fragment = DrawingFragment()
        val args = Bundle()
        args.putInt("index",index)
        args.putString("type",type)
        fragment.arguments = args
        return fragment
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDrawingBinding.inflate(inflater, container, false)
        imageView=binding.bitmap
        binding.root.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    floatStartX = event.x
                    floatStartY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    floatEndX = event.x
                    floatEndY = event.y
                    drawPaintSketchImage() // Implement your drawing logic here
                    floatStartX = event.x
                    floatStartY = event.y
                }
                MotionEvent.ACTION_UP -> {
                    floatEndX = event.x
                    floatEndY = event.y
                    drawPaintSketchImage() // Implement your drawing logic here
                }
            }
            true // Return true to indicate the event was handled
        }

        return binding.root
    }
    private fun drawPaintSketchImage() {
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(
                imageView!!.width,
                imageView!!.height,
                Bitmap.Config.ARGB_8888
            )
            canvas = Canvas(bitmap!!)
            paint.color = Color.WHITE
            paint.isAntiAlias = true
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 30f
        }
        canvas!!.drawLine(
            floatStartX,
            floatStartY-50,
            floatEndX,
            floatEndY-50 ,
            paint
        )
        imageView!!.setImageBitmap(bitmap)
    }

}