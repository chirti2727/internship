package com.ttl.zenith.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ttl.zenith.MainActivity
import com.ttl.zenith.R
import com.ttl.zenith.adapter.AvatarAdapter
import com.ttl.zenith.databinding.FragmentAvaterBinding


class AvatarFragment : Fragment() {
    private val REQUEST_CAMERA_PERMISSION = 1
    private val REQUEST_IMAGE_CAPTURE = 2
    private val REQUEST_IMAGE_GALLERY = 3
    private lateinit var imageView: ImageView

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private var _binding : FragmentAvaterBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(imageBitmap)
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val imageUri = data?.data
                imageView.setImageURI(imageUri)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAvaterBinding.inflate(inflater,container,false)
        binding.cameraButton.setOnClickListener {
            if (checkCameraPermission()) {
                openCamera()
            } else {
                requestCameraPermission()
            }
        }

        // Gallery button click event
        binding.galleryButton.setOnClickListener {
            if (checkGalleryPermission()) {
                openGallery()
            } else {
                requestGalleryPermission()
            }
        }
        //binding.avatarView.avatarInitials = "AA"
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val avatarImages = intArrayOf(
            R.drawable.man,
            R.drawable.gamer,
            R.drawable.woman,
            R.drawable.hacker,
            R.drawable.gamer,
            R.drawable.hacker,
            R.drawable.man,
            R.drawable.woman,
            R.drawable.woman,
            R.drawable.man,
            R.drawable.hacker,
            R.drawable.gamer,
            R.drawable.man,
            R.drawable.gamer,
            R.drawable.woman,
            R.drawable.hacker
        )
        val avatarAdapter = AvatarAdapter(avatarImages)
        binding.avatarRecyclerView.adapter = avatarAdapter
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
    private fun checkCameraPermission(): Boolean {
        val cameraPermission = Manifest.permission.CAMERA
        return ContextCompat.checkSelfPermission(requireContext(), cameraPermission) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkGalleryPermission(): Boolean {
        val galleryPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        return ContextCompat.checkSelfPermission(requireContext(), galleryPermission) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestCameraPermission() {
        val cameraPermission = Manifest.permission.CAMERA
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(cameraPermission), REQUEST_CAMERA_PERMISSION)
    }

    private fun requestGalleryPermission() {
        val galleryPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(galleryPermission), REQUEST_IMAGE_GALLERY)
    }




    // Handle permission request result
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION-> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    // Handle permission denied
                }
            }
            REQUEST_IMAGE_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    // Handle permission denied
                }
            }
        }
    }
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(takePictureIntent)
    }
    private fun openGallery() {
        val pickPictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(pickPictureIntent)
    }


}
