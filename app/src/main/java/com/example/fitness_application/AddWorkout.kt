package com.example.fitness_application

import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_workout.*
import java.util.*

class AddWorkout : AppCompatActivity() {
    var dbManager:WorkoutsDatabase?=null
    private lateinit var actionBar: ActionBar
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar = supportActionBar!!
        actionBar.title = "Lisää treeni"
        setContentView(R.layout.activity_add_workout)
        dbManager = DBManager().getDatabase(applicationContext)



       try{
           var bundle:Bundle= intent.extras!!
           id=bundle.getInt("ID",0)
           if(id!=0) {
               etTitle.setText(bundle.getString("name") )
               etDes.setText(bundle.getString("des") )

           }
       }catch (ex:Exception){}


    }

    fun  buAdd(view:View){

        var values= ContentValues()
        values.put("Title",etTitle.text.toString())
        values.put("Description",etDes.text.toString())
        val date: Int = Date().getDate()
        val formatedDate: String = SimpleDateFormat("dd-MM-yyyy").format(Date())

        if(id==0) {

             dbManager!!.WorkoutsDao().insert( Workouts(0,etTitle.text.toString(),etDes.text.toString(),formatedDate))
             finish()

        }else{

                dbManager!!.WorkoutsDao().update( Workouts(id,etTitle.text.toString(),etDes.text.toString(),formatedDate))
                finish()

        }

    }
}


