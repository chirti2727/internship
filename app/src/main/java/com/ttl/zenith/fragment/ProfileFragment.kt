package com.ttl.zenith.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ttl.zenith.R
import com.ttl.zenith.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        //binding.avatarView.avatarInitials = "AA"
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardChangePassword.setOnClickListener {
         val navController = Navigation.findNavController(view)
            navController.navigate(R.id.changePasswordFragment)
        }
        binding.knowYourUser.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.kyufragment)
        }

    }


}