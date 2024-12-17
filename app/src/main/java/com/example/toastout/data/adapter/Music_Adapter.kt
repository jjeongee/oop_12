package com.example.toastout.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 음악 데이터를 표시하는 어댑터 클래스
class Music_Adapter(
    private val musicResIds: List<Int>, // 음악 트랙 리소스 ID 목록
    private val onItemClick: (Int) -> Unit // 클릭 이벤트 콜백 함수
) : RecyclerView.Adapter<Music_Adapter.MusicViewHolder>() {

    // 뷰 홀더를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        // 아이템 뷰를 생성 (android 기본 레이아웃 사용: simple_list_item_1)
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return MusicViewHolder(view) // 뷰 홀더 반환
    }

    // 뷰 홀더와 데이터를 연결하는 메서드
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        // 각 위치에 맞는 트랙 이름과 클릭 이벤트를 설정
        holder.bind("Track ${position + 1}", position)
    }

    // 아이템 개수를 반환하는 메서드
    override fun getItemCount(): Int = musicResIds.size // 트랙의 총 개수

    // 뷰 홀더 클래스: RecyclerView의 각 아이템을 표시
    inner class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(android.R.id.text1) // TextView 참조

        // 뷰 홀더에 데이터와 클릭 이벤트를 설정
        fun bind(title: String, position: Int) {
            textView.text = title // 트랙 이름 설정
            itemView.setOnClickListener {
                onItemClick(position) // 아이템 클릭 시 콜백 함수 호출
            }
        }
    }
}