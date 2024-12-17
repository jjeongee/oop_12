package com.example.toastout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// MusicAdapter 클래스: RecyclerView에 음악 목록을 표시해주는 역할
class MusicAdapter(
    private val musicList: List<Music>,       // 음악 목록을 담는 리스트
    private var currentTrackIndex: Int,       // 현재 선택된 음악의 위치(index)
    private val onItemClick: (Int) -> Unit    // 음악을 클릭했을 때 실행할 함수 (콜백 함수)
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    // 현재 선택된 음악의 위치를 업데이트하는 함수
    fun updateCurrentTrackIndex(newIndex: Int) {
        currentTrackIndex = newIndex   // 새로운 선택된 위치로 업데이트
        notifyDataSetChanged()         // 화면을 새로 고침 (리스트를 다시 그려줌)
    }

    // 새로운 ViewHolder를 만들 때 호출됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        // 아이템 뷰(item_music.xml)를 화면에 그리기 위해 LayoutInflater를 사용
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view)   // ViewHolder에 뷰를 넣어 반환
    }

    // ViewHolder와 데이터를 연결해주는 함수 (리스트의 각 아이템 설정)
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = musicList[position]        // 현재 위치의 음악 데이터를 가져옴
        val isSelected = position == currentTrackIndex // 현재 음악이 선택된 음악인지 확인
        holder.bind(music, position, isSelected) // ViewHolder에 데이터와 상태를 전달
    }

    // 음악 목록의 개수를 반환
    override fun getItemCount(): Int = musicList.size

    // ViewHolder 클래스: 각 아이템의 뷰를 관리
    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // 뷰에서 필요한 UI 요소(썸네일과 제목)를 찾아서 변수에 저장
        private val thumbnail: ImageView = view.findViewById(R.id.thumbnailImageView)
        private val title: TextView = view.findViewById(R.id.titleTextView)

        // 뷰에 데이터를 설정하는 함수
        fun bind(music: Music, position: Int, isSelected: Boolean) {
            thumbnail.setImageResource(music.thumbnailResId) // 썸네일 이미지를 설정
            title.text = if (isSelected) "▶ ${music.title}" else music.title // 선택된 음악에는 '▶'를 붙임

            // 선택된 음악은 배경 색을 다르게 설정 (강조 효과)
            itemView.setBackgroundColor(
                if (isSelected) 0xFFE0E0E0.toInt() else 0xFFFFFFFF.toInt()
            )

            // 음악 아이템을 클릭했을 때 실행될 동작 (콜백 함수 호출)
            itemView.setOnClickListener {
                onItemClick(position) // 클릭된 음악의 위치를 콜백 함수에 전달
            }
        }
    }
}