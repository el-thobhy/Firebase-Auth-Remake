package com.elthobhy.viefirebaseauth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elthobhy.viefirebaseauth.databinding.ActivitySigninBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionbar()
        onCLick()
    }

    private fun onCLick() {
        binding.apply {
            btnSignIn.setOnClickListener {
                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
            }
            btnForgotPass.setOnClickListener {
                startActivity(Intent(this@SignInActivity, ForgotPasswordActivity::class.java))
            }
            tbSignIn.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun initActionbar() {
        setSupportActionBar(binding.tbSignIn)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}