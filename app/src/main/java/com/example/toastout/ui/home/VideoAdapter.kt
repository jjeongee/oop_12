package com.example.toastout.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.toastout.R

// VideoAdapter 클래스: 비디오 목록을 RecyclerView에 표시하는 역할
class VideoAdapter(
    private val videoUrls: List<Uri>, // 비디오 URL을 담는 리스트
    private val onItemClick: (Int) -> Unit // 비디오를 클릭했을 때 실행할 함수, 번호 전달 -> 근데 Unit 이라 반환값 X
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        // 비디오 아이템의 모양(item_video.xml)을 화면에 표시
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false) // 레이아웃!!!
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        // 다음 비디오 -> 다음 -> 다음...
        holder.bind(videoUrls[position], position)
    }

    override fun getItemCount(): Int = videoUrls.size

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // thumbnail 이 썸네일 스펠링이었단 사실을 처음 알았다.
        private val imageView: ImageView = view.findViewById(R.id.thumbnail_image)

        // 비디오 데이터를 화면에 보여주는 함수
        fun bind(uri: Uri, position: Int) {
            // 기본 썸네일 이미지를 설정 (임시 이미지), 근데 사용 X => 내가 다 썸네일 설정해둠
            imageView.setImageResource(R.drawable.default_thumbnail)

            // 콜백
            itemView.setOnClickListener {
                onItemClick(position)
            }
        }
    }
}