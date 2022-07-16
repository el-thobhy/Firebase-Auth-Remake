package com.elthobhy.viefirebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elthobhy.viefirebaseauth.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private var currentUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        currentUser = FirebaseAuth.getInstance().currentUser
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

    override fun onStart() {
        super.onStart()
        currentUser?.let {
            startActivity(Intent(this,MainActivity::class.java))
            finishAffinity()
        }
    }
}