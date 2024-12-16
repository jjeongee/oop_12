package com.example.toastout.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.toastout.R

class VideoAdapter(
    private val videoUrls: List<Uri>, // 비디오 URL 목록
    private val onItemClick: (Int) -> Unit // 클릭 이벤트 콜백
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        // 비디오 썸네일 또는 이미지 설정
        holder.bind(videoUrls[position], position)
    }

    override fun getItemCount(): Int = videoUrls.size

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.thumbnail_image)

        fun bind(uri: Uri, position: Int) {
            // 썸네일 이미지 설정 예시
            imageView.setImageResource(R.drawable.default_thumbnail) // 기본 썸네일
            itemView.setOnClickListener { onItemClick(position) }
        }
    }
}