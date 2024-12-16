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
import com.example.toastout.MusicAdapter
import com.example.toastout.R
import com.google.firebase.storage.FirebaseStorage

class Home_Fragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null
    private val firebaseStorage = FirebaseStorage.getInstance()
    private val musicFileNames = listOf( // Firebase Storage에 업로드된 파일 이름
        "APT.mp3",
        "diewith.mp3"
    )
    private var currentTrackIndex = 0
    private val musicUrls = mutableListOf<String>() // Firebase URL 저장 리스트

    private lateinit var musicRecyclerView: RecyclerView
    private lateinit var adapter: MusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicRecyclerView = view.findViewById(R.id.recycler_view_music)
        musicRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Firebase에서 URL 가져오기
        loadMusicUrls {
            adapter = MusicAdapter(musicUrls, currentTrackIndex) { position ->
                currentTrackIndex = position
                playMusicFromFirebase(musicUrls[position])
                adapter.notifyDataSetChanged()
            }
            musicRecyclerView.adapter = adapter

            val leftButton = view.findViewById<Button>(R.id.left_button_music)
            val rightButton = view.findViewById<Button>(R.id.right_button_music)

            leftButton.setOnClickListener { navigateMusic(-1) }
            rightButton.setOnClickListener { navigateMusic(1) }
        }
    }

    private fun loadMusicUrls(onComplete: () -> Unit) {
        val storageRef = firebaseStorage.reference.child("music") // Firebase Storage 'music' 폴더
        var count = 0
        musicFileNames.forEach { fileName ->
            storageRef.child(fileName).downloadUrl.addOnSuccessListener { uri ->
                println("Loaded URL: ${uri.toString()}") // 성공한 URL 출력
                musicUrls.add(uri.toString()) // URL 추가
                count++
                if (count == musicFileNames.size) {
                    println("All URLs loaded: $musicUrls") // 최종 리스트 확인
                    onComplete()
                }
            }.addOnFailureListener { exception ->
                println("Failed to load URL for $fileName: ${exception.message}")
            }
        }
    }

    private fun playMusicFromFirebase(url: String) {
        println("Playing URL: $url")
        mediaPlayer?.release()
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener { start() }
                setOnErrorListener { _, what, extra ->
                    println("MediaPlayer Error: what=$what, extra=$extra")
                    true
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private fun navigateMusic(direction: Int) {
        if (musicUrls.isNotEmpty()) {
            currentTrackIndex = (currentTrackIndex + direction + musicUrls.size) % musicUrls.size
            playMusicFromFirebase(musicUrls[currentTrackIndex])
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}