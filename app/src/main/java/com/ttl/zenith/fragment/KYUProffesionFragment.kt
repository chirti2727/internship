package com.ttl.zenith.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ttl.zenith.MainActivity
import com.ttl.zenith.R
import com.ttl.zenith.databinding.FragmentKYUBinding

class KYUProffesionFragment : Fragment() {
    private var _binding : FragmentKYUBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentKYUBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val staterArray = arrayOf("West Bengal", "Odhisa", "Kerala", "Assam", "Punjab")
        var stateAdapter = context?.let { ArrayAdapter<String>(it, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,staterArray) }
        binding.tvproffesion.setAdapter(stateAdapter)
        binding.tvproffesion.threshold = 1
        binding.tvproffesion.setOnClickListener {
            binding.tvproffesion.showDropDown()
        }

        binding.skip.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.kyucityfragment)
        }
        binding.next.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.kyucityfragment)
        }
        binding.avatarView.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.avaterFragment)
        }
        binding.previous.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.profileFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.showBottomNavigation(false)
        (activity as MainActivity?)!!.showToolBar(false)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity?)!!.showBottomNavigation(true)
        (activity as MainActivity?)!!.showToolBar(true)
    }

}