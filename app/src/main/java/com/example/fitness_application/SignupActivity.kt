package com.example.fitness_application

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.fitness_application.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {



    //ViewBinding
    private lateinit var  binding: ActivitySignupBinding

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
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Rekisteröidy"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Odota")
        progressDialog.setMessage("Rekisteröi...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            confirmData()
        }
        binding.backToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun confirmData() {
        email = binding.emailEdit.text.toString().trim()
        psw = binding.pswEdit.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEdit.error = "Virhe sähköpostissa"
        } else if(TextUtils.isEmpty(psw)){
            binding.pswEdit.error = "Syötä salasana"
        } else if(psw.length<6){
            binding.pswEdit.error = "Vähintään 6 merkkiä salasanassa"
        } else {
            firebaseSignup()
        }
    }

    private fun firebaseSignup() {
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, psw)
            .addOnSuccessListener {
                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Käyttäjä luotu sähköpostilla $email", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
