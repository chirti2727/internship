package com.ttl.zenith.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ttl.zenith.MainActivity
import com.ttl.zenith.R
import com.ttl.zenith.databinding.FragmentHomeBinding
import com.ttl.zenith.databinding.FragmentOnlineTestBinding
import com.ttl.zenith.model.Message
import com.ttl.zenith.model.Question
import com.ttl.zenith.model.Quiz
import com.ttl.zenith.utils.Constant
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class OnlineTestFragment : Fragment() {
    var count = 0
    var totalCount:Int = 0
    var Answer = ""
    lateinit var timer: CountDownTimer
    private var _binding : FragmentOnlineTestBinding? = null
    private val binding get() = _binding!!

    val JSON: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
    var client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnlineTestBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setCourses()

    }

    private fun setCourses() {

        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.Course_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spnCourses.adapter = adapter

        val levelAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.Level_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spnLevel.adapter = levelAdapter

        binding.spnCourses.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getQuestionFromZenith()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.spnLevel.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getQuestionFromZenith()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    private fun getQuestionFromZenith() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.llQuestion.visibility = View.INVISIBLE
        binding.cardView.visibility = View.INVISIBLE
        if (binding.spnCourses.selectedItemPosition!=0&&binding.spnLevel.selectedItemPosition!=0){
            val courses=binding.spnCourses.selectedItem.toString()
            val level=binding.spnLevel.selectedItem.toString()
            val strMessage="Generate 2 $courses MCQ with correct options with $level difficulty level. JSON Format"
            Log.e("strMessage",strMessage)

callAPI(strMessage)






        }
    }
    fun callAPI(question: String?) {

        binding.progressBar.visibility = View.VISIBLE
        binding.llQuestion.visibility = View.INVISIBLE

        val jsonBody = JSONObject()
        try {
            jsonBody.put("model", "text-davinci-003")
            jsonBody.put("prompt", question)
            jsonBody.put("max_tokens", 4000)
            jsonBody.put("temperature", 0)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body: RequestBody = RequestBody.create(JSON, jsonBody.toString())



        val request: Request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization", "Bearer ${Constant.API_KEY_CHAT}")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(),"Failed to load response due to " + e.message,Toast.LENGTH_SHORT).show()
binding.progressBar.visibility=View.INVISIBLE
                }


            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response.body.string())

                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getString("text")
                        Log.e("result",result.toString())

                        val gson = Gson()
                        val listType = object : TypeToken<List<Question>>() {}.type
                        val questionList: List<Question> = gson.fromJson(result, listType)


                        activity?.runOnUiThread {
                            val handler = Handler()

                            val displayQuestionsRunnable = object : Runnable {
                                var index = 0 // Keep track of the current question index

                                override fun run() {
                                    if (index < questionList.size) {

                                        val question = questionList[index]
                                        val questionText = question.question
                                        binding.textQuestion.text =questionText
                                        binding.buttonA.text = question.options[0]
                                        binding.buttonB.text = question.options[1]
                                        binding.buttonC.text = question.options[2]
                                        binding.buttonD.text = question.options[3]
                                        binding.progressBar.visibility = View.INVISIBLE
                                        binding.llQuestion.visibility = View.VISIBLE
                                        binding.cardView.visibility=View.VISIBLE
                                        count += 1
                                        index++
                                        handler.postDelayed(this, 20000)
                                    }
                                }
                            }


                            handler.post(displayQuestionsRunnable)
                        }
                        //callData(questionList)



                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {


                }
            }
        })
    }

    private fun callData(questionList: List<Question>) {
        val handler = Handler()

        val displayQuestionsRunnable = object : Runnable {
            var index = 0 // Keep track of the current question index

            override fun run() {
                if (index < questionList.size) {
                    val question = questionList[index]
                    val questionText = question.question
                    binding.textQuestion.text =questionText
                    binding.buttonA.text = question.options[0]
                    binding.buttonB.text = question.options[1]
                    binding.buttonC.text = question.options[2]
                    binding.buttonD.text = question.options[3]
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.llQuestion.visibility = View.VISIBLE
                    count += 1
                    index++
                    handler.postDelayed(this, 30000)
                }
            }
        }


        handler.post(displayQuestionsRunnable)

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