package com.ttl.zenith

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide


class ForgetPassword : AppCompatActivity() {
    private lateinit var emailEdit: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        emailEdit = findViewById(R.id.email_edit_text)
        val resetButton: Button = findViewById(R.id.reset_button)
        val adImageView: ImageView = findViewById(R.id.adImageView)
        val imageUrl ="https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.fotor.com%2Fblog%2Fbest-advertisement-examples%2F&psig=AOvVaw2xTMf3O5t9ND13nLNrVdDc&ust=1687358836137000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCKifuNKL0v8CFQAAAAAdAAAAABAI"
        Glide.with(this)
            .load(imageUrl)
            .into(adImageView)
        resetButton.setOnClickListener {
            startActivity(Intent(this,ResetPassword::class.java))
            finish()

            // Perform password reset logic here
        }
    }
}