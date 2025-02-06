package com.example.dislexiaapp2025.ui.tracing

import android.annotation.SuppressLint
import android.graphics.Path
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dislexiaapp2025.databinding.FragmentTracingBinding
import com.example.dislexiaapp2025.repo.local.LettersAndNumbers
import com.example.dislexiaapp2025.repo.local.TracingPaths

class TracingFragment : Fragment() {
    private lateinit var binding: FragmentTracingBinding

    private var pathsSize: Int = 0
    private var completedPaths:Int=0
    private lateinit var paths:ArrayList<Path>


    fun getInstance(index:Int,type:String): TracingFragment {
        val fragment = TracingFragment()
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

        binding = FragmentTracingBinding.inflate(inflater, container, false)

        val index=arguments?.getInt("index")!!
        val type=arguments?.getString("type")
        paths = when(type){
           "letter"->{
               TracingPaths.getLetterPaths(index)
           }
            else->{
                TracingPaths.getNumberPaths(index)
            }
        }
        val tracingName: String = when(type){
          "letter"->{
              LettersAndNumbers.letters[index].name
          }
          else->{
              LettersAndNumbers.numbers[index].name}
      }
        binding.TracingView.setNewPathArray(paths,tracingName)
        Toast.makeText(requireContext(), "index $index : $tracingName", Toast.LENGTH_SHORT).show()
        //binding.TracingView.getInstance(paths)

        //set background
       // val bg=this.arguments?.getInt("fragmentBg",R.drawable.a_draw)
        //imageView=binding.imageView
       // binding.beginnerDrawFragmentContainer.setBackgroundResource(bg!!)
        /**/


        return binding.root
    }
    fun getResult():Pair<Int,Int>{
        pathsSize=binding.TracingView.pathArray.size
        completedPaths=binding.TracingView.completedPaths.size
        return Pair(pathsSize,completedPaths)
    }
    /**/



}