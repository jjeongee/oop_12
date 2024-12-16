package com.example.toastout.ui.home

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toastout.Music
import com.example.toastout.MusicAdapter
import com.example.toastout.R
import com.google.firebase.storage.FirebaseStorage

class Home_Fragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null
    private val firebaseStorage = FirebaseStorage.getInstance()

    // 음악 파일 이름과 썸네일 매핑
    private val musicFileNames = listOf("APT.mp3", "diewith.mp3", "pocketlocket.mp3", "numberonegirl.mp3",
        "lastchristmas.mp3")
    private val thumbnailMap = mapOf(
        "APT.mp3" to R.drawable.apt,
        "diewith.mp3" to R.drawable.diewith,
        "pocketlocket.mp3" to R.drawable.pocketlocket,
        "numberonegirl.mp3" to R.drawable.numberonegirl,
        "lastchristmas.mp3" to R.drawable.lastchristmas
    )

    private var currentTrackIndex = 0
    private val musicList = mutableListOf<Music>() // Music 리스트

    private lateinit var adapter: MusicAdapter
    private lateinit var musicRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        musicRecyclerView = view.findViewById(R.id.recycler_view_music)
        musicRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Firebase에서 음악 URL 가져오기
        loadMusicUrls {
            adapter = MusicAdapter(musicList, currentTrackIndex) { position ->
                currentTrackIndex = position
                playMusicFromFirebase(musicList[position].url)
                adapter.updateCurrentTrackIndex(currentTrackIndex)
            }
            musicRecyclerView.adapter = adapter
        }

        // 버튼 클릭 이벤트 설정
        view.findViewById<Button>(R.id.left_button_music).setOnClickListener { navigateMusic(-1) }
        view.findViewById<Button>(R.id.right_button_music).setOnClickListener { navigateMusic(1) }
    }

    private fun loadMusicUrls(onComplete: () -> Unit) {
        val storageRef = firebaseStorage.reference.child("music")
        var count = 0
        musicFileNames.forEach { fileName ->
            storageRef.child(fileName).downloadUrl.addOnSuccessListener { uri ->
                musicList.add(
                    Music(
                        title = fileName.removeSuffix(".mp3"), // 파일 이름에서 .mp3 제거
                        url = uri.toString(),                // Firebase에서 가져온 URL
                        thumbnailResId = thumbnailMap[fileName] ?: R.drawable.default_thumbnail
                    )
                )
                count++
                if (count == musicFileNames.size) onComplete()
            }.addOnFailureListener { exception ->
                println("Failed to load URL for $fileName: ${exception.message}")
            }
        }
    }

    private fun playMusicFromFirebase(url: String) {
        mediaPlayer?.release() // 이전 MediaPlayer 해제
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { start() }
        }
    }

    private fun navigateMusic(direction: Int) {
        if (musicList.isNotEmpty()) {
            // 현재 트랙 인덱스 업데이트
            currentTrackIndex = (currentTrackIndex + direction + musicList.size) % musicList.size

            // 음악 재생
            playMusicFromFirebase(musicList[currentTrackIndex].url)

            // 어댑터에 새로운 트랙 인덱스 업데이트
            adapter.updateCurrentTrackIndex(currentTrackIndex)

            // RecyclerView를 현재 인덱스 위치로 스크롤
            musicRecyclerView.smoothScrollToPosition(currentTrackIndex)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}