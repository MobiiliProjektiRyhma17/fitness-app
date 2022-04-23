package com.example.fitness_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class PlansActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var planRecyclerView: RecyclerView
    private  lateinit var planArrayList: ArrayList<plan>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plans)


        planRecyclerView = findViewById(R.id.planList)
        planRecyclerView.layoutManager = LinearLayoutManager(this)
        planRecyclerView.setHasFixedSize(true)

        planArrayList = arrayListOf<plan>()
        getPlanData()



    }

    private fun getPlanData() {

        dbRef = FirebaseDatabase.getInstance().getReference("plans")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for(planSnapshot in snapshot.children){

                        val plan = planSnapshot.getValue(plan::class.java)
                        planArrayList.add(plan!!)
                    }

                    planRecyclerView.adapter =MyAdapter(planArrayList)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}