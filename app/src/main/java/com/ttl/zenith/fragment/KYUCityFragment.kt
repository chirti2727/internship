package com.ttl.zenith.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.Navigation
import com.ttl.zenith.MainActivity
import com.ttl.zenith.R
import com.ttl.zenith.databinding.FragmentKYUCityBinding


class KYUCityFragment : Fragment() {

    private var _binding : FragmentKYUCityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentKYUCityBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryArray = arrayOf("India", "America", "Australia", "Africa", "London", "China")
        var countryAdapter = context?.let { ArrayAdapter<String>(it, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,countryArray) }
        binding.tvCountry.setAdapter(countryAdapter)
        binding.tvCountry.threshold = 1
        binding.tvCountry.setOnClickListener {
            binding.tvCountry.showDropDown()
        }



        binding.skip.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.teacherSelectionFragment)
        }
        binding.next.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.teacherSelectionFragment)
        }
        binding.avatarView.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.avaterFragment)
        }

        binding.previous.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.kyufragment)
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