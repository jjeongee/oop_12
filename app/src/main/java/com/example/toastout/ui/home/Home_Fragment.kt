package com.example.toastout.ui.home

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toastout.Music
import com.example.toastout.MusicAdapter
import com.example.toastout.R
import com.google.firebase.storage.FirebaseStorage
import kotlin.random.Random

class Home_Fragment : Fragment() {

    // Firebase Storage 인스턴스 초기화
    private val firebaseStorage = FirebaseStorage.getInstance()

    // 음악 관련 변수
    private var mediaPlayer: MediaPlayer? = null // 음악 재생기
    private var isMusicPlaying = false // 음악 재생 상태를 저장
    private val musicFileNames = listOf( // Firebase에서 가져올 음악 파일명
        "APT.mp3", "diewith.mp3", "pocketlocket.mp3", "numberonegirl.mp3", "lastchristmas.mp3"
    )
    private val thumbnailMap = mapOf( // 음악에 맞는 썸네일 이미지 매핑
        "APT.mp3" to R.drawable.apt,
        "diewith.mp3" to R.drawable.diewith,
        "pocketlocket.mp3" to R.drawable.pocketlocket,
        "numberonegirl.mp3" to R.drawable.numberonegirl,
        "lastchristmas.mp3" to R.drawable.lastchristmas
    )
    private var currentTrackIndex = 0 // 현재 재생 중인 음악의 인덱스
    private val musicList = mutableListOf<Music>() // 음악 데이터를 저장할 리스트
    private lateinit var musicAdapter: MusicAdapter // RecyclerView에 사용할 어댑터
    private lateinit var musicRecyclerView: RecyclerView // 음악 목록을 보여줄 RecyclerView

    // 비디오 관련 변수
    private lateinit var videoView: VideoView // 비디오를 재생할 VideoView
    private val videoFolders = listOf("cat", "dog", "hamster") // 비디오 폴더 목록
    private val videoUrls = mutableListOf<Uri>() // 비디오 파일들의 URL을 저장하는 리스트
    private var currentVideoIndex = 0 // 현재 재생 중인 비디오의 인덱스
    private var isVideoPlaying = false // 비디오 재생 상태를 저장

    // View를 생성하고 화면에 표시
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    // View가 생성된 후 호출
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 비디오 뷰 설정
        videoView = view.findViewById(R.id.video_view)

        // 음악 RecyclerView 설정
        musicRecyclerView = view.findViewById(R.id.recycler_view_music)
        musicRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Firebase에서 음악과 비디오 로드
        loadMusicUrls { setupMusicAdapter() } // 음악 데이터 불러오기
        loadRandomVideoUrls { playVideo(videoUrls[currentVideoIndex]) } // 랜덤 비디오 URL 불러오기

        // 비디오 및 음악 이동 버튼 설정
        view.findViewById<Button>(R.id.left_button_video).setOnClickListener { navigateVideo(-1) }
        view.findViewById<Button>(R.id.right_button_video).setOnClickListener { navigateVideo(1) }
        view.findViewById<Button>(R.id.left_button_music).setOnClickListener { navigateMusic(-1) }
        view.findViewById<Button>(R.id.right_button_music).setOnClickListener { navigateMusic(1) }

        // 비디오 클릭 시 재생/일시정지 설정
        videoView.setOnClickListener { toggleVideo() }
    }

    // 음악 어댑터를 설정
    private fun setupMusicAdapter() {
        musicAdapter = MusicAdapter(musicList, currentTrackIndex) { position ->
            currentTrackIndex = position
            playMusicFromFirebase(musicList[position].url) // 클릭된 음악 재생
        }
        musicRecyclerView.adapter = musicAdapter
        musicRecyclerView.setOnClickListener { toggleMusic() }
    }

    // Firebase에서 음악 URL들을 불러오는 함수
    private fun loadMusicUrls(onComplete: () -> Unit) {
        val storageRef = firebaseStorage.reference.child("music") // "music" 폴더 참조
        musicFileNames.forEach { fileName ->
            storageRef.child(fileName).downloadUrl.addOnSuccessListener { uri ->
                // 음악 데이터를 리스트에 추가
                musicList.add(
                    Music(
                        title = fileName.removeSuffix(".mp3"),
                        url = uri.toString(),
                        thumbnailResId = thumbnailMap[fileName] ?: R.drawable.default_thumbnail
                    )
                )
                if (musicList.size == musicFileNames.size) onComplete() // 모든 음악 로드 완료 시 실행
            }
        }
    }

    // Firebase에서 랜덤 비디오 폴더의 비디오 URL을 불러오는 함수
    private fun loadRandomVideoUrls(onComplete: () -> Unit) {
        val randomFolder = videoFolders.random() // 랜덤 폴더 선택
        val storageRef = firebaseStorage.reference.child(randomFolder)
        videoUrls.clear() // 기존 비디오 URL 리스트 초기화
        storageRef.listAll().addOnSuccessListener { result ->
            result.items.forEach { videoRef ->
                videoRef.downloadUrl.addOnSuccessListener { uri ->
                    videoUrls.add(uri) // 비디오 URL 추가
                    if (videoUrls.size == result.items.size) onComplete() // 모든 URL 로드 완료 시 실행
                }
            }
        }
    }

    // Firebase URL에서 음악을 재생하는 함수
    private fun playMusicFromFirebase(url: String) {
        mediaPlayer?.release() // 기존 음악 정지
        isMusicPlaying = false
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                start()
                isMusicPlaying = true
            }
        }
    }

    // 비디오를 재생하는 함수
    private fun playVideo(videoUrl: Uri) {
        videoView.setVideoURI(videoUrl)
        videoView.setOnPreparedListener {
            videoView.start()
            isVideoPlaying = true
        }
    }

    // 음악 일시정지/재생 전환
    private fun toggleMusic() {
        mediaPlayer?.let {
            if (isMusicPlaying) it.pause() else it.start()
            isMusicPlaying = !isMusicPlaying
        }
    }

    // 비디오 일시정지/재생 전환
    private fun toggleVideo() {
        if (isVideoPlaying) videoView.pause() else videoView.start()
        isVideoPlaying = !isVideoPlaying
    }

    // 음악 목록에서 다음/이전 곡으로 이동
    private fun navigateMusic(direction: Int) {
        if (musicList.isNotEmpty()) {
            currentTrackIndex = (currentTrackIndex + direction + musicList.size) % musicList.size
            playMusicFromFirebase(musicList[currentTrackIndex].url)
            musicAdapter.updateCurrentTrackIndex(currentTrackIndex)
            musicRecyclerView.smoothScrollToPosition(currentTrackIndex)
        }
    }

    // 비디오 목록에서 다음/이전 비디오로 이동
    private fun navigateVideo(direction: Int) {
        if (videoUrls.isNotEmpty()) {
            currentVideoIndex = (currentVideoIndex + direction + videoUrls.size) % videoUrls.size
            playVideo(videoUrls[currentVideoIndex])
        }
    }

    // Fragment 종료 시 MediaPlayer 해제
    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
    }
}