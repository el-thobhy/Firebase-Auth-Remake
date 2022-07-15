package com.elthobhy.viefirebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elthobhy.viefirebaseauth.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick() {
        binding.apply {
            btnSignInAuth.setOnClickListener {
                startActivity(Intent(this@AuthActivity,SignInActivity::class.java))
            }
            btnSignUpAuth.setOnClickListener {
                startActivity(Intent(this@AuthActivity,SignUpActivity::class.java))
            }
        }
    }
}