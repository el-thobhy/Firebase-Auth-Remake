package com.elthobhy.viefirebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elthobhy.viefirebaseauth.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionbar()
    }

    private fun initActionbar() {
        setSupportActionBar(binding.tbForgotPass)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}