package com.example.dislexiaapp2025.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dislexiaapp2025.model.Letter
import com.example.dislexiaapp2025.databinding.LetterNumItemBinding
import com.example.dislexiaapp2025.util.listener.LettersListener

class LettersAdapter(private val data: List<Letter>,val listener: LettersListener) :
    RecyclerView.Adapter<LettersAdapter.LetterHolder>() {

    // Create ViewHolder class
    class LetterHolder(val binding: LetterNumItemBinding) : RecyclerView.ViewHolder(binding.root)

    // Create ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterHolder {
        val binding =
            LetterNumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterHolder(binding)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: LetterHolder, position: Int) {
        holder.binding.image.setImageResource(data[position].imageId)
        holder.binding.root.setOnClickListener{
            listener.OnClick(data[position],position)
        }
    }

    // Return the size of the data list
    override fun getItemCount(): Int {
        return data.size
    }
}