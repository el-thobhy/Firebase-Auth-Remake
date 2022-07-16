package com.elthobhy.viefirebaseauth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elthobhy.viefirebaseauth.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        dialogLoading = showDialogLoading(this)
        setContentView(binding.root)

        initActionBar()
        onClick()
    }

    private fun onClick() {
        binding.apply {
            btnSignUp.setOnClickListener {
                val email = etEmailSignUp.text.toString().trim()
                val pass = etPasswordSignUp.text.toString().trim()
                val confirmPass = etConfirmPasswordSignUp.text.toString().trim()
                if(validationCheck(email, pass, confirmPass)){
                    hideSoftKeyboard(this@SignUpActivity,binding.root)
                    dialogLoading.show()
                    signUpToServer(email, pass)
                }
            }
            tbSignUp.setNavigationOnClickListener{
                finish()
            }
        }
    }

    private fun signUpToServer(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    dialogLoading.dismiss()
                    val dialogSuccess = showDialogSuccess(this, "Success Sign In")
                    dialogSuccess.show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        dialogSuccess.dismiss()
                        finish()
                    },2000)
                }else{
                    dialogLoading.dismiss()
                    showDialogError(this,"Sign Up Failed")
                }
            }
            .addOnFailureListener {
                dialogLoading.dismiss()
                showDialogError(this,it.message.toString())
            }
    }

    private fun validationCheck(email: String, pass: String, confirmPass: String): Boolean {
        binding.apply {
            when{
                email.isEmpty() ->{
                    etEmailSignUp.error = "Please Field Your Email"
                    etEmailSignUp.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                    etEmailSignUp.error = "Please Use Valid Email"
                    etEmailSignUp.requestFocus()
                }
                pass.isEmpty()->{
                    etPasswordSignUp.error = "Please Field Your Password"
                    etPasswordSignUp.requestFocus()
                }
                confirmPass.isEmpty()->{
                    etConfirmPasswordSignUp.error = "Please Field Your Confirm Password"
                    etConfirmPasswordSignUp.requestFocus()
                }
                pass != confirmPass -> {
                    etPasswordSignUp.error = "Your Confirm Password didn't match with your password"
                    etPasswordSignUp.requestFocus()
                    etConfirmPasswordSignUp.error = "Your Confirm Password didn't match with your password"
                    etConfirmPasswordSignUp.requestFocus()
                }
                else->{
                    return true
                }

            }
        }
        return false
    }

    private fun initActionBar() {
        setSupportActionBar(binding.tbSignUp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}