package com.elthobhy.viefirebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elthobhy.viefirebaseauth.R
import com.elthobhy.viefirebaseauth.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}