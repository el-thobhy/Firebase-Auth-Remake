package com.elthobhy.viefirebaseauth

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
    }

    private fun initActionbar() {
        setSupportActionBar(binding.tbSignIn)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}