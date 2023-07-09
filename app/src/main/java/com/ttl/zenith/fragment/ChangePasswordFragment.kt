package com.ttl.zenith.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ttl.zenith.MainActivity
import com.ttl.zenith.R
import com.ttl.zenith.databinding.FragmentChagePasswordBinding


class ChangePasswordFragment : Fragment() {

    private var _binding : FragmentChagePasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChagePasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.avatarView.setOnClickListener {
            val   navController = Navigation.findNavController(view)
            navController.navigate(R.id.avaterFragment)
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