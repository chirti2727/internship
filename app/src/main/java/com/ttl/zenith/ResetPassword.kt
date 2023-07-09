package com.ttl.zenith

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide

class ResetPassword : AppCompatActivity() {
    private lateinit var newPasswordEdit: EditText
    private lateinit var confirmPasswordEdit: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        newPasswordEdit = findViewById(R.id.new_password_edit_text)
        confirmPasswordEdit = findViewById(R.id.confirm_password_edit_text)
        var resetButton: Button = findViewById(R.id.reset_button)
        val adImageView: ImageView = findViewById(R.id.adImageView)
        val imageUrl ="https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.fotor.com%2Fblog%2Fbest-advertisement-examples%2F&psig=AOvVaw2xTMf3O5t9ND13nLNrVdDc&ust=1687358836137000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCKifuNKL0v8CFQAAAAAdAAAAABAI"
        Glide.with(this)
            .load(imageUrl)
            .into(adImageView)
        resetButton.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()

            // Perform password reset logic here
        }
    }
}