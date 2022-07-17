package com.elthobhy.viefirebaseauth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elthobhy.viefirebaseauth.databinding.ActivitySigninBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dialogLoading: AlertDialog
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        dialogLoading = showDialogLoading(this)
        initGoogleSignIn()
        callbackManager = CallbackManager.Factory.create()
        setContentView(binding.root)

        initActionbar()
        onCLick()
    }

    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

    private var resultLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        if(result.resultCode == Activity.RESULT_OK){
                dialogLoading.show()
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    signInToServer(credential)
                }catch (e:ApiException){
                    dialogLoading.dismiss()
                    showDialogError(this,e.message.toString())
                }
        }
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
            btnFacebookSignIn.setOnClickListener {
                dialogLoading.show()
                loginFacebook()
            }
            btnGoogleSignIn.setOnClickListener {
                val signInIntent = mGoogleSignInClient.signInIntent
                resultLaunch.launch(signInIntent)
            }
            tbSignIn.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
        LoginManager.getInstance().registerCallback(callbackManager,
        object : FacebookCallback<LoginResult>{
            override fun onCancel() {
                dialogLoading.dismiss()
                showDialogError(this@SignInActivity,"Fail To Login")
            }

            override fun onError(error: FacebookException) {
                dialogLoading.dismiss()
                showDialogError(this@SignInActivity, error.message.toString())
            }

            override fun onSuccess(result: LoginResult) {
                dialogLoading.dismiss()
                val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                signInToServer(credential)
            }

        })
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
                    etEmailSignIn.error = "Please Field Your Email"
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