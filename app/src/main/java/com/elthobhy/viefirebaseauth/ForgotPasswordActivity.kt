package com.elthobhy.viefirebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.elthobhy.viefirebaseauth.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionbar()
        onClick()
    }

    private fun onClick() {
        binding.apply {
            btnSendEmail.setOnClickListener {
                Toast.makeText(this@ForgotPasswordActivity,"sent",Toast.LENGTH_SHORT).show()
            }
            tbForgotPass.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun initActionbar() {
        setSupportActionBar(binding.tbForgotPass)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}