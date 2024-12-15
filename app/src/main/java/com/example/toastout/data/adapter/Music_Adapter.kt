package com.example.toastout.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Music_Adapter(
    private val musicResIds: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<Music_Adapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind("Track ${position + 1}", position)
    }

    override fun getItemCount(): Int = musicResIds.size

    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(android.R.id.text1)

        fun bind(title: String, position: Int) {
            textView.text = title
            itemView.setOnClickListener {
                onItemClick(position) // 아이템 클릭 이벤트 전달
            }
        }
    }
}