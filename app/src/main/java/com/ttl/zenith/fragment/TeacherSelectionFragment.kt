package com.ttl.zenith.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ttl.zenith.MainActivity
import com.ttl.zenith.R
import com.ttl.zenith.databinding.FragmentKYUCityBinding
import com.ttl.zenith.databinding.FragmentTeacherSelectionBinding


class TeacherSelectionFragment : Fragment() {

    private var _binding : FragmentTeacherSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherSelectionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previous.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.kyucityfragment)
        }
        binding.skip.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.profileFragment)
        }
        binding.next.setOnClickListener {
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