package com.ttl.zenith.Videos.adapter

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ttl.zenith.R
import com.ttl.zenith.Videos.model.VideoData
import com.ttl.zenith.databinding.MyvideosItemBinding
import com.ttl.zenith.fragment.Videos.VideoPlayPage

class MyVideoAdapter(
    private var videoItems: ArrayList<VideoData>):RecyclerView.Adapter<MyVideoAdapter.ViewHolder>(),
    MediaPlayer.OnPreparedListener {

    var context: Context? = null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MyvideosItemBinding.bind(itemView)
        val thumbnail = binding.imageView
        val videoView = binding.videoView2
        val progressBar = binding.progressBar
        val videoTitle = binding.textVideoTitle
        val videoDesc = binding.textVideoDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view= LayoutInflater.from(context).inflate(R.layout.myvideos_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.progressBar.visibility = View.GONE
        val movie = videoItems[position]
        holder.videoTitle.text = movie.videoTitle
        holder.thumbnail.load(movie.thumbnailUrl){
            crossfade(true)
            crossfade(500)
        }

        holder. videoView.setVideoPath(movie.videoURL)

        holder.thumbnail.setOnClickListener {
            val intent = Intent(context,VideoPlayPage::class.java)
            intent.putExtra("videoUrl",movie.videoURL)
            intent.putExtra("videoTitle",movie.videoTitle)
            intent.putExtra("videoDesc",movie.videoDesc)
            context?.startActivity(intent)
            /*holder.thumbnail.visibility = View.INVISIBLE
            holder.videoView.visibility = View.VISIBLE
            holder. videoView.setOnPreparedListener { mp ->
                holder. progressBar.visibility = View.GONE
                mp.start()
            }
            holder. videoView.setOnCompletionListener { mp -> mp.setOnPreparedListener(this) }*/
        }

        /*holder.videoView.setOnClickListener {
            if (holder.videoView.isPlaying){
                holder.videoView.pause()
            }
            else{
                holder.videoView.start()
            }
        }*/

    }

    override fun onPrepared(p0: MediaPlayer?) {
        p0?.start()
    }
}