package com.example.toastout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 음악 목록 표시하기(썸네일 기타 등등...)
class MusicAdapter(
    private val musicList: List<Music>,       // 왜 에러가 뜨는데 정상 작동되지
    private var currentTrackIndex: Int,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    // 업데이트
    fun updateCurrentTrackIndex(newIndex: Int) {
        currentTrackIndex = newIndex   // 새로운 선택된 위치로 업데이트
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        // LayoutInflater를 사용해서 아이템 뷰 만들게 하기
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)   // 뷰홀더에 뷰를 넣어 반환
    }

    // ViewHolder와 데이터를 연결
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = musicList[position]
        val isSelected = position == currentTrackIndex
        holder.bind(music, position, isSelected)
    }

    override fun getItemCount(): Int = musicList.size

    // 아까 거기서 쓰기
    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val thumbnail: ImageView = view.findViewById(R.id.thumbnailImageView)
        private val title: TextView = view.findViewById(R.id.titleTextView)

        // 뷰에 데이터를 설정
        fun bind(music: Music, position: Int, isSelected: Boolean) {
            thumbnail.setImageResource(music.thumbnailResId)
            title.text = if (isSelected) "▶ ${music.title}" else music.title

            itemView.setBackgroundColor(
                if (isSelected) 0xFFE0E0E0.toInt() else 0xFFFFFFFF.toInt()
            )

            itemView.setOnClickListener {
                onItemClick(position) // 콜백
            }
        }
    }
}