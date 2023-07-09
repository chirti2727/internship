package com.ttl.zenith.fragment.Videos

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ttl.zenith.R
import com.ttl.zenith.databinding.ActivityVideoPlayPageBinding


class VideoPlayPage : AppCompatActivity(), MediaPlayer.OnPreparedListener {
    private lateinit var binding : ActivityVideoPlayPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val videoView = binding.videoView
        val videoControls = binding.llVideocontrol
        videoControls.visibility = View.GONE

        val layoutParams = videoView.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        videoView.layoutParams = layoutParams

        val videoUrl = intent.getStringExtra("videoUrl")
        val videoTitle = intent.getStringExtra("videoTitle")
        val videoDesc = intent.getStringExtra("videoDesc")
        binding.textVideoTitle.text = videoTitle
        binding.textVideoDescription.text = videoDesc

        videoView.setVideoPath(videoUrl)
        videoView.setOnPreparedListener { it.start() }
        videoView.setOnCompletionListener { it.setOnPreparedListener(this) }
        videoView.setOnClickListener {
            if (videoView.isPlaying){
                videoView.pause()
                videoControls.visibility = View.VISIBLE
            }
        }
        binding.playbtn.setOnClickListener {
            videoView.start()
            videoControls.visibility = View.GONE
        }
        binding.btnVideoDescVisibility.setOnClickListener {
            if (binding.textVideoDescription.isVisible){
                binding.btnVideoDescVisibility.setImageResource(R.drawable.arrow_drop_down)
                binding.textVideoDescription.visibility = View.GONE
            }
            else{
                binding.btnVideoDescVisibility.setImageResource(R.drawable.arrow_drop_up)
                binding.textVideoDescription.visibility = View.VISIBLE
            }
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        binding.progressBar.visibility = View.GONE
        mp?.start()
    }
}