package com.elthobhy.viefirebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.widget.Toast
import com.elthobhy.viefirebaseauth.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)

        initActionbar()
        onClick()
    }

    private fun onClick() {
        binding.apply {
            btnSendEmail.setOnClickListener {
                val email = etEmailForgotPass.text.toString().trim()
                if(email.isEmpty()){
                    etEmailForgotPass.error = "Please Field Your Email"
                    etEmailForgotPass.requestFocus()
                    return@setOnClickListener
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmailForgotPass.error = "Please Use Valid Email"
                    etEmailForgotPass.requestFocus()
                    return@setOnClickListener
                }else{
                    goToServerPassword(email)
                }
            }
            tbForgotPass.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun goToServerPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    Toast.makeText(this, "Your Link reset Has Been Send", Toast.LENGTH_SHORT).show()
                    val dialogSuccess = showDialogSuccess(this,"Your Link reset Has Been Send")
                    dialogSuccess.show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        dialogSuccess.dismiss()
                        startActivity(Intent(this, SignInActivity::class.java))
                        finishAffinity()
                    },2000)
                }else{
                    showDialogError(this, "Fail To reset Password")
                }
            }
            .addOnFailureListener {
                showDialogError(this,it.message.toString())
            }
    }

    private fun initActionbar() {
        setSupportActionBar(binding.tbForgotPass)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}