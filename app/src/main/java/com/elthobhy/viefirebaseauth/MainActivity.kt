package com.elthobhy.viefirebaseauth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elthobhy.viefirebaseauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)

        getData()
        onClick()
    }

    private fun getData() {
        val user = firebaseAuth.currentUser
        user?.let {
            binding.tvEmail.text = user.email
        }
    }

    private fun onClick() {
        binding.apply {
            btnLogout.setOnClickListener {
                firebaseAuth.signOut()
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                finishAffinity()
            }
        }
    }
}
