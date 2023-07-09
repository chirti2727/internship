package com.ttl.zenith.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ttl.zenith.R
import com.ttl.zenith.Videos.adapter.MyVideoAdapter
import com.ttl.zenith.Videos.model.VideoData


class MyVideoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videosViewPager: RecyclerView = view.findViewById(R.id.videoViewPager)
        val videoItems: ArrayList<VideoData> = ArrayList()

        val item = VideoData(
            videoDesc = "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.\\n\\nLicensed under the Creative Commons Attribution license\\nhttp://www.bigbuckbunny.org",
            videoTitle = "Big Buck Bunny", thumbnailUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Big_Buck_Bunny_-_forest.jpg/800px-Big_Buck_Bunny_-_forest.jpg?20081010152945.jpg", videoURL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        val item1 = VideoData(
            videoDesc = "The first Blender Open Movie from 2006",
            videoTitle = "Elephant Dream", thumbnailUrl = "https://www.spiritanimals.org/wp-content/uploads/2022/05/elephant-815x556.jpg", videoURL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
//        val item2 = VideoData(videoDesc = "HBO GO now works with Chromecast -- the easiest way to enjoy online video on your TV. For when you want to settle into your Iron Throne to watch the latest episodes. For \$35.\\nLearn how to use Chromecast with HBO GO and more at google.com/chromecast.",videoTitle = "For Bigger Blazes", thumbnailUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fsnack.expo.dev%2F%40jenshandersson%2Foptimized-video-flatlist&psig=AOvVaw2FjHcnnhXbq7JLo0nXG3IK&ust=1681694347537000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCMC_-eKdrf4CFQAAAAAdAAAAABAI",videoURL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")

        videoItems.add(item)
        videoItems.add(item1)


        videosViewPager.layoutManager = LinearLayoutManager(context)
        videosViewPager.adapter = MyVideoAdapter(videoItems)
    }


}