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
    private val onItemClick: (Int) -> Unit // 비디오를 클릭했을 때 실행할 함수 (콜백 함수)
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    // 새로운 뷰(ViewHolder)를 만들 때 호출됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        // 비디오 아이템의 모양(item_video.xml)을 화면에 표시하는 준비를 함
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false) // 레이아웃을 가져와서 뷰로 만듦
        return VideoViewHolder(view) // 만든 뷰를 ViewHolder에 넣어 반환
    }

    // ViewHolder에 비디오 데이터를 설정해주는 함수
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        // 비디오 URL과 위치를 ViewHolder에 넘겨서 화면에 보여주게 함
        holder.bind(videoUrls[position], position)
    }

    // 비디오 목록의 개수를 반환 (RecyclerView가 몇 개의 아이템을 보여줘야 할지 알려줌)
    override fun getItemCount(): Int = videoUrls.size

    // ViewHolder 클래스: 각 비디오 아이템의 화면을 관리함
    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // 아이템 뷰에서 썸네일 이미지뷰를 찾아 저장
        private val imageView: ImageView = view.findViewById(R.id.thumbnail_image)

        // 비디오 데이터를 화면에 보여주는 함수
        fun bind(uri: Uri, position: Int) {
            // 기본 썸네일 이미지를 설정 (임시 이미지)
            imageView.setImageResource(R.drawable.default_thumbnail)

            // 아이템(비디오)이 클릭되면 해당 비디오의 위치를 콜백 함수에 전달
            itemView.setOnClickListener {
                onItemClick(position) // 클릭한 비디오의 위치를 알려줌
            }
        }
    }
}