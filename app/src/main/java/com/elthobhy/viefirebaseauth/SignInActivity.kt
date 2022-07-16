package com.elthobhy.viefirebaseauth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elthobhy.viefirebaseauth.databinding.ActivitySigninBinding
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        dialogLoading = showDialogLoading(this)
        setContentView(binding.root)

        initActionbar()
        onCLick()
    }

    private fun onCLick() {
        binding.apply {
            btnSignIn.setOnClickListener {
                val email = etEmailSignIn.text.toString().trim()
                val pass = etPasswordSignIn.text.toString().trim()
                if(validationCheck(email, pass)){
                    dialogLoading.show()
                    loginCredential(email, pass)
                }
            }
            btnForgotPass.setOnClickListener {
                startActivity(Intent(this@SignInActivity, ForgotPasswordActivity::class.java))
            }
            tbSignIn.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun loginCredential(email: String, pass: String) {
        val credential = EmailAuthProvider.getCredential(email, pass)
        signInToServer(credential)
    }

    private fun signInToServer(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task->
                dialogLoading.dismiss()
                if(task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }else{
                    showDialogError(this, "Failed To Login")
                }
            }
            .addOnFailureListener {
                dialogLoading.dismiss()
                showDialogError(this,it.message.toString())
            }
    }

    private fun validationCheck(email: String, pass: String): Boolean {
        binding.apply {
            when{
                email.isEmpty() -> {
                    etEmailSignIn.error = "Pleasae Field Your Email"
                    etEmailSignIn.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    etEmailSignIn.error = "Please Use Valid Email"
                    etEmailSignIn.requestFocus()
                }
                pass.isEmpty() -> {
                    etPasswordSignIn.error = "Please Field Your Password"
                    etPasswordSignIn.requestFocus()
                }
                else -> {
                    return true
                }
            }
        }
        return false
    }

    private fun initActionbar() {
        setSupportActionBar(binding.tbSignIn)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}