package com.example.toastout

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Recommend_Fragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null
    private val musicResIds = listOf(
        R.raw.roxanne,
        R.raw.unknown
    )
    private var currentTrackIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_recommend.xml 레이아웃을 사용
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        val musicRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_music)
        musicRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        musicRecyclerView.adapter = MusicAdapter(musicResIds) { position ->
            playLocalMusic(position) // RecyclerView 아이템 클릭 시 로컬 음악 재생
        }

        // 왼쪽/오른쪽 버튼 설정
        val leftButton = view.findViewById<Button>(R.id.left_button_music)
        val rightButton = view.findViewById<Button>(R.id.right_button_music)

        leftButton.setOnClickListener {
            navigateMusic(-1) // 이전 곡
        }
        rightButton.setOnClickListener {
            navigateMusic(1) // 다음 곡
        }
    }

    private fun playLocalMusic(index: Int) {
        mediaPlayer?.release() // 이전 MediaPlayer 해제
        try {
            mediaPlayer = MediaPlayer.create(requireContext(), musicResIds[index])
            mediaPlayer?.apply {
                setOnPreparedListener {
                    println("Music is prepared and will start playing") // 준비 완료 로그
                    start() // 재생 시작
                }
                setOnErrorListener { mp, what, extra ->
                    println("MediaPlayer Error: what=$what, extra=$extra") // 에러 로그
                    release()
                    true
                }
            }
        } catch (e: Exception) {
            println("Exception occurred: ${e.message}") // 예외 로그
            e.printStackTrace()
        }
    }

    private fun navigateMusic(direction: Int) {
        currentTrackIndex = (currentTrackIndex + direction + musicResIds.size) % musicResIds.size
        playLocalMusic(currentTrackIndex)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release() // MediaPlayer 리소스 해제
        mediaPlayer = null
    }
}