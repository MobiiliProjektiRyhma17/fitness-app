package com.example.fitness_application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val planList : ArrayList<plan>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.plan_item,
        parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentplan = planList[position]



        holder.plan.text = currentplan.plan
        holder.target.text = currentplan.target

    }

    override fun getItemCount(): Int {
        return planList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val target : TextView = itemView.findViewById(R.id.tvArea)
        val plan : TextView = itemView.findViewById(R.id.tvPlan)



    }
}