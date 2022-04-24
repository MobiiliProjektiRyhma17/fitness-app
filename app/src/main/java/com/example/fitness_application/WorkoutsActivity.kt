package com.example.fitness_application

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.fitness_application.databinding.ActivityAddWorkoutBinding
import com.example.fitness_application.databinding.ActivityWorkoutsBinding
import kotlinx.android.synthetic.main.activity_workouts.*
import kotlinx.android.synthetic.main.workout_item.view.*


class WorkoutsActivity : AppCompatActivity() {

    var listNotes=ArrayList<Workouts>()
    var dbManager:WorkoutsDatabase?=null

    private lateinit var actionBar: ActionBar
    private lateinit var binding: ActivityWorkoutsBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        actionBar = supportActionBar!!
        actionBar.title = "Päiväkirja"

        binding = ActivityWorkoutsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dbManager = DBManager().getDatabase(applicationContext)

        LoadQuery("%")


    }

    override  fun onResume() {
        super.onResume()
        LoadQuery("%")
    }




    fun LoadQuery(title:String){

        listNotes = dbManager!!.WorkoutsDao().loadByTitle(title) as ArrayList<Workouts>


        var myWorkoutsAdapter= MyWorkoutsAdpater(this, listNotes)
        lvWorkouts.adapter=myWorkoutsAdapter


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

          menuInflater.inflate(R.menu.main_menu, menu)

          val sv: SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
          sv.queryHint = "Etsi treeniä...";
          val sm= getSystemService(Context.SEARCH_SERVICE) as SearchManager
          sv.setSearchableInfo(sm.getSearchableInfo(componentName))
          sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
              override fun onQueryTextSubmit(query: String): Boolean {
                  LoadQuery("%$query%")
                  return false
              }

              override fun onQueryTextChange(newText: String): Boolean {
                  return false
              }
          })


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (item != null) {
                when(item.itemId){
                    R.id.addNote->{
                        var intent= Intent(this,AddWorkout::class.java)
                        startActivity(intent)
                    }
                }
            }
            return super.onOptionsItemSelected(item)
    }

    inner class  MyWorkoutsAdpater:BaseAdapter{
        var listWorkoutsAdpater=ArrayList<Workouts>()
        var context:Context?=null
        constructor(context:Context, listWorkoutsAdpater:ArrayList<Workouts>):super(){
            this.listWorkoutsAdpater=listWorkoutsAdpater
            this.context=context
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            var myView=layoutInflater.inflate(R.layout.workout_item,null)

            var myWorkout=listWorkoutsAdpater[p0]
            myView.tvTitle.text=myWorkout.Title
            myView.tvDes.text=myWorkout.Description
            myView.tvDate.text=myWorkout.Date
            myView.ivDelete.setOnClickListener{

                dbManager!!.WorkoutsDao().delete(myWorkout)

                LoadQuery("%")
            }
            myView.ivEdit.setOnClickListener{

                GoToUpdate(myWorkout)

            }
            return myView
        }

        override fun getItem(p0: Int): Any {
        return listWorkoutsAdpater[p0]
         }

        override fun getItemId(p0: Int): Long {
           return p0.toLong()
         }

        override fun getCount(): Int {

            return listWorkoutsAdpater.size

        }



    }


   fun GoToUpdate(workout:Workouts){
       var intent=  Intent(this,AddWorkout::class.java)
       intent.putExtra("ID",workout.ID)
       intent.putExtra("name",workout.Title)
       intent.putExtra("des",workout.Description)
       startActivity(intent)
   }

    fun main(args: Array<String>) {

        val chars = listOf('å', 'ä', 'ö')
        val (letters, notLetters) = chars.partition { it.isLetter() }
        println(letters) // [a, β]
        println(notLetters) // [+, 1]

    }


}
