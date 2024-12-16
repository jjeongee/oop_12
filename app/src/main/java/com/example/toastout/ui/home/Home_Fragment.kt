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

    private val firebaseStorage = FirebaseStorage.getInstance()

    // 음악 관련 변수
    private var mediaPlayer: MediaPlayer? = null
    private var isMusicPlaying = false
    private val musicFileNames = listOf(
        "APT.mp3",
        "diewith.mp3",
        "pocketlocket.mp3",
        "numberonegirl.mp3",
        "lastchristmas.mp3"
    )
    private val thumbnailMap = mapOf(
        "APT.mp3" to R.drawable.apt,
        "diewith.mp3" to R.drawable.diewith,
        "pocketlocket.mp3" to R.drawable.pocketlocket,
        "numberonegirl.mp3" to R.drawable.numberonegirl,
        "lastchristmas.mp3" to R.drawable.lastchristmas
    )
    private var currentTrackIndex = 0
    private val musicList = mutableListOf<Music>()
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var musicRecyclerView: RecyclerView

    // 비디오 관련 변수
    private lateinit var videoView: VideoView
    private val videoFolders = listOf("cat", "dog", "hamster")
    private val videoUrls = mutableListOf<Uri>()
    private var currentVideoIndex = 0
    private var isVideoPlaying = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // VideoView 설정
        videoView = view.findViewById(R.id.video_view)
        // 음악 RecyclerView 설정
        musicRecyclerView = view.findViewById(R.id.recycler_view_music)
        musicRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Firebase 음악 및 영상 로드
        loadMusicUrls {
            setupMusicAdapter()
        }
        loadRandomVideoUrls {
            playVideo(videoUrls[currentVideoIndex])
        }

        // 버튼 이벤트 설정
        view.findViewById<Button>(R.id.left_button_video).setOnClickListener { navigateVideo(-1) }
        view.findViewById<Button>(R.id.right_button_video).setOnClickListener { navigateVideo(1) }
        view.findViewById<Button>(R.id.left_button_music).setOnClickListener { navigateMusic(-1) }
        view.findViewById<Button>(R.id.right_button_music).setOnClickListener { navigateMusic(1) }
        // 비디오 및 음악 토글 이벤트
        videoView.setOnClickListener { toggleVideo() }
    }

    private fun setupMusicAdapter() {
        musicAdapter = MusicAdapter(musicList, currentTrackIndex) { position ->
            currentTrackIndex = position
            playMusicFromFirebase(musicList[position].url)
        }
        musicRecyclerView.adapter = musicAdapter
        musicRecyclerView.setOnClickListener { toggleMusic() }
    }

    private fun loadMusicUrls(onComplete: () -> Unit) {
        val storageRef = firebaseStorage.reference.child("music")
        musicFileNames.forEach { fileName ->
            storageRef.child(fileName).downloadUrl.addOnSuccessListener { uri ->
                musicList.add(
                    Music(
                        title = fileName.removeSuffix(".mp3"),
                        url = uri.toString(),
                        thumbnailResId = thumbnailMap[fileName] ?: R.drawable.default_thumbnail
                    )
                )
                if (musicList.size == musicFileNames.size) onComplete()
            }
        }
    }

    private fun loadRandomVideoUrls(onComplete: () -> Unit) {
        val randomFolder = videoFolders.random()
        val storageRef = firebaseStorage.reference.child(randomFolder)
        videoUrls.clear()
        storageRef.listAll().addOnSuccessListener { result ->
            result.items.forEach { videoRef ->
                videoRef.downloadUrl.addOnSuccessListener { uri ->
                    videoUrls.add(uri)
                    if (videoUrls.size == result.items.size) onComplete()
                }
            }
        }
    }

    private fun playMusicFromFirebase(url: String) {
        mediaPlayer?.release()
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

    private fun playVideo(videoUrl: Uri) {
        videoView.setVideoURI(videoUrl)
        videoView.setOnPreparedListener {
            videoView.start()
            isVideoPlaying = true
        }
    }

    private fun toggleMusic() {
        mediaPlayer?.let {
            if (isMusicPlaying) it.pause() else it.start()
            isMusicPlaying = !isMusicPlaying
        }
    }

    private fun toggleVideo() {
        if (isVideoPlaying) videoView.pause() else videoView.start()
        isVideoPlaying = !isVideoPlaying
    }

    private fun navigateMusic(direction: Int) {
        if (musicList.isNotEmpty()) {
            currentTrackIndex = (currentTrackIndex + direction + musicList.size) % musicList.size
            playMusicFromFirebase(musicList[currentTrackIndex].url)
            musicAdapter.updateCurrentTrackIndex(currentTrackIndex)
            musicRecyclerView.smoothScrollToPosition(currentTrackIndex)
        }
    }

    private fun navigateVideo(direction: Int) {
        if (videoUrls.isNotEmpty()) {
            currentVideoIndex = (currentVideoIndex + direction + videoUrls.size) % videoUrls.size
            playVideo(videoUrls[currentVideoIndex])
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
    }
}