package com.example.toastout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(
    private val musicList: List<Music>,
    private var currentTrackIndex: Int,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    fun updateCurrentTrackIndex(newIndex: Int) {
        currentTrackIndex = newIndex
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_music, parent, false) // 새 레이아웃 사용
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = musicList[position]
        holder.bind(music, position, position == currentTrackIndex)
    }

    override fun getItemCount(): Int = musicList.size

    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val thumbnail: ImageView = view.findViewById(R.id.thumbnailImageView)
        private val title: TextView = view.findViewById(R.id.titleTextView)

        fun bind(music: Music, position: Int, isSelected: Boolean) {
            thumbnail.setImageResource(music.thumbnailResId) // 썸네일 이미지 설정
            title.text = if (isSelected) "▶ ${music.title}" else music.title

            // 선택된 트랙 강조
            itemView.setBackgroundColor(
                if (isSelected) 0xFFE0E0E0.toInt() else 0xFFFFFFFF.toInt()
            )

            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }
}