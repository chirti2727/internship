package com.ttl.zenith

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ttl.zenith.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE

            Handler().postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                binding.progressBar.visibility=View.GONE
            }, 3000)

        }
        binding.signUp.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this,ForgetPassword::class.java))
            finish()
        }
    }
}