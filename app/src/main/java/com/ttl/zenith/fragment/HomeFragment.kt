package com.ttl.zenith.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ttl.zenith.R
import com.ttl.zenith.databinding.FragmentHomeBinding
import com.ttl.zenith.databinding.FragmentTeacherSelectionBinding


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.useChatGpt.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.chatFragment)

        }

        binding.useGenerateImage.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.imageProcessFragment)
        }
        binding.useOnlineTest.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.onlineTestFragment)
        }
    }


}