package com.ttl.zenith.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ttl.zenith.MainActivity
import com.ttl.zenith.adapter.MessageAdapter
import com.ttl.zenith.databinding.FragmentChatBinding
import com.ttl.zenith.model.Message
import com.ttl.zenith.utils.Constant
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ChatFragment : Fragment() {
private var chatBinding:FragmentChatBinding?=null
    private val binding get() = chatBinding!!

    var messageList: ArrayList<Message>? = null
    var messageAdapter: MessageAdapter? = null
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
        chatBinding = FragmentChatBinding.inflate(inflater,container,false)
        return binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageList=ArrayList()
        //setup recycler view
        messageAdapter = MessageAdapter(messageList!!)
        binding. recyclerView.adapter = messageAdapter
        val llm = LinearLayoutManager(requireContext())
        llm.stackFromEnd = true
        binding. recyclerView.layoutManager = llm
binding.sendBtn.setOnClickListener {
    val question: String = binding.messageEditText.getText().toString().trim { it <= ' ' }
    addToChat(question, Message.SENT_BY_ME)
    binding.messageEditText.setText("")
    callAPI(question)
    binding.welcomeText.visibility = View.GONE
}
    }
    fun addToChat(message: String?, sentBy: String?) {

        activity?.runOnUiThread {
            messageList?.add(Message(message!!, sentBy!!))
            messageAdapter!!.notifyDataSetChanged()
            binding.recyclerView.smoothScrollToPosition(messageAdapter!!.itemCount)

        }

    }

    fun addResponse(response: String?) {
        messageList?.removeAt(messageList!!.size - 1)
        addToChat(response, Message.SENT_BY_BOT)
    }

    fun callAPI(question: String?) {
        //okhttp
        messageList?.add(Message("Typing... ", Message.SENT_BY_BOT))
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
                addResponse("Failed to load response due to " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response.body.string())
                        Log.e("jsonObject",jsonObject.toString())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getString("text")
                        Log.e("result",result.toString())
                        addResponse(result.trim { it <= ' ' })
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    addResponse("Failed to load response due to " + response.body.toString())
                }
            }
        })
    }

}