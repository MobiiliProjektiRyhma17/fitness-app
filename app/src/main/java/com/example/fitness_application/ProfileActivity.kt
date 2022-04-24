package com.example.fitness_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.ActionBar
import com.example.fitness_application.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {


    private lateinit var nameEdit: EditText
    private lateinit var addressEdit: EditText
    private lateinit var phoneNumberEdit: EditText
    private lateinit  var goalsMenu: AutoCompleteTextView
    private lateinit  var shapesMenu: AutoCompleteTextView
    private lateinit var editButton: Button




    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Profiili"

        databaseReference = FirebaseDatabase.getInstance().getReference("Profiles")

        nameEdit = findViewById(R.id.nameEdit)
        addressEdit = findViewById(R.id.addressEdit)
        phoneNumberEdit = findViewById(R.id.phoneNumberEdit)
        editButton = findViewById(R.id.editButton)

        editButton.setOnClickListener {
            saveProduct()
        }

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logoutButton.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }

        binding.homeIb.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.calendarIb.setOnClickListener {
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
        }

        binding.calendarIb.setOnClickListener{
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
        }

        binding.timerIb.setOnClickListener{
            val intent = Intent(this, WorkoutsActivity::class.java)
            startActivity(intent)
        }


        val goals = resources.getStringArray(R.array.goals)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item,goals)
        val shapes = resources.getStringArray(R.array.shapes)
        val arrayAdapter2 = ArrayAdapter(this,R.layout.dropdown_item,shapes)
        goalsMenu = findViewById<AutoCompleteTextView>(R.id.autoGoal)
        shapesMenu = findViewById<AutoCompleteTextView>(R.id.shapeSelect)
        goalsMenu.setAdapter(arrayAdapter)
        shapesMenu.setAdapter(arrayAdapter2)

    }

    class Profile(val ID: String, val Nimi: String, val Osoite: String, val Puhelinnumero: String, val Tavoite: String, val Kuntotaso: String){

        constructor() : this("","","","", "", ""){

        }
    }

    private fun saveProduct() {
        val name = nameEdit.text.toString().trim()
        val address = addressEdit.text.toString().trim()
        val number =  phoneNumberEdit.text.toString().trim()
        val goal = goalsMenu.text.toString().trim()
        val shape = shapesMenu.text.toString().trim()

        if(name.isEmpty()){
            nameEdit.error = "Ole hyvä ja syötä nimesi"
            return
        }

        val profileID: String? = databaseReference.push().key

        val info = Profile(profileID.toString(), name, address, number, goal, shape)

        databaseReference.child(profileID.toString()).setValue(info).addOnCompleteListener {
            Toast.makeText(applicationContext, "Tiedot päivitetty onnistuneesti", Toast.LENGTH_LONG).show()
        }

    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser !=null){
            val email = firebaseUser.email
            binding.showEmail.text = email
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}