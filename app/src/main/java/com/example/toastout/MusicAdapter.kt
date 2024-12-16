package com.example.toastout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(
    private val musicUrls: List<String>,
    private val currentTrackIndex: Int,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val musicTitle = "Track ${position + 1}"
        println("Binding: $musicTitle") // 바인딩 확인 로그
        holder.bind(musicTitle, position)

        // 현재 트랙 강조 표시
        if (position == currentTrackIndex) {
            holder.itemView.setBackgroundColor(0xFFE0E0E0.toInt()) // 회색 강조
        } else {
            holder.itemView.setBackgroundColor(0xFFFFFFFF.toInt()) // 기본 배경색
        }
    }

    override fun getItemCount(): Int = musicUrls.size

    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(android.R.id.text1)

        fun bind(title: String, position: Int) {
            textView.text = title
            itemView.setOnClickListener { onItemClick(position) }
        }
    }
}