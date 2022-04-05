package com.example.fitness_application

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.fitness_application.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    //ViewBinding
    private lateinit var binding: ActivityLoginBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var psw = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Kirjaudu sisään"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Odota")
        progressDialog.setMessage("Kirjautuu sisään")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.noAccount.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            confirmData()
        }
    }

    private fun confirmData() {
        email = binding.emailEdit.text.toString().trim()
        psw = binding.pswEdit.text.toString().trim()

        //confirmation
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEdit.error = "Tarkista että syötit sähköpostin oikein"
        } else if (TextUtils.isEmpty(psw)) {
            binding.pswEdit.error = "Syötä salasana"
        } else {
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, psw)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Kirjautunut sisään $email", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                //already logged in
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
        }
    }
}

