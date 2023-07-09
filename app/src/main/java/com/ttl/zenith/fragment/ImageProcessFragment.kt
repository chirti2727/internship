package com.ttl.zenith.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.ttl.zenith.MainActivity
import com.ttl.zenith.databinding.FragmentImageProcessBinding
import com.ttl.zenith.utils.Constant
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ImageProcessFragment : Fragment() {
    var client = OkHttpClient()
    private var _binding: FragmentImageProcessBinding? = null
    private val binding get() = _binding!!
    var imageUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImageProcessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.generateBtn.setOnClickListener {

            val text: String = binding.inputText.text.toString().trim { it <= ' ' }
            if (text.isEmpty()) {
                binding.inputText.error = "Text can't be empty"
                return@setOnClickListener
            }
            hideKeyboard(requireActivity())
            callAPI(text)
        }
        binding.imgShare.setOnClickListener {


            if (imageUrl!!.isNotEmpty()) {
                shareImage(imageUrl)


            } else {
                Toast.makeText(requireContext(), "Image can not be empty", Toast.LENGTH_SHORT)
                    .show()
            }

        }


    }


    fun shareImage(imageUrl: String) {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"

        try {
            Glide.with(requireContext())
                .asBitmap()
                .load(imageUrl)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val file = File(requireContext().cacheDir, "image.png")
                        val outputStream = FileOutputStream(file)
                        resource.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        outputStream.flush()
                        outputStream.close()

                        val uriFile = FileProvider.getUriForFile(
                            requireContext(),
                            "${requireContext().packageName}.provider", file
                        )

                        intent.putExtra(Intent.EXTRA_STREAM, uriFile)
                        startActivity(Intent.createChooser(intent, "Share image using"))
                    }
                })
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error sharing image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun callAPI(text: String?) {
        //API CALL
        setInProgress(true)
        val jsonBody = JSONObject()
        try {
            jsonBody.put("prompt", text)
            jsonBody.put("size", "256x256")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val JSON: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
        val body: RequestBody = RequestBody.create(JSON, jsonBody.toString())


        val request: Request = Request.Builder()
            .url("https://api.openai.com/v1/images/generations")
            .header("Authorization", "Bearer  ${Constant.API_KEY_CHAT}")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(
                    requireContext(),
                    "Failed to generate image",
                    Toast.LENGTH_LONG
                ).show()
                setInProgress(false)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonObject = JSONObject(response.body.string())
                    val url = jsonObject.getJSONArray("data").getJSONObject(0).getString("url")
                    imageUrl = url


                    loadImage(url)
                    setInProgress(false)
                } catch (e: Exception) {
                    setInProgress(false)
                    e.printStackTrace()
                }
            }
        })
    }

    fun setInProgress(inProgress: Boolean) {
        activity?.runOnUiThread {
            if (inProgress) {
                binding.progressBar.visibility = View.VISIBLE
                binding.generateBtn.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.generateBtn.visibility = View.VISIBLE
            }
        }

    }

    fun loadImage(url: String?) {
        //load image


        activity?.runOnUiThread {
            Glide.with(binding.imageView)
                .load(url)
                .into(binding.imageView)

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

    fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}