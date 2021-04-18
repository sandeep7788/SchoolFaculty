package com.education.Vidhyalaya_Faculty_APP.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.education.Vidhyalaya_Faculty_APP.API.AttendanceList
import com.education.Vidhyalaya_Faculty_APP.R
import java.util.*

class AttendenceAdapter(var context: Context, val userList: ArrayList<AttendanceList>) : RecyclerView.Adapter<AttendenceAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.attendenceadapter, parent, false)




        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.t_date.setText(" "+userList.get(position).date.toString())
        Log.e("@@"," "+userList.get(position).status.toString().toLowerCase().trim())

        if((" "+userList.get(position).status.toString()).toLowerCase().trim().equals("p"))
        {
            holder.t_status.setText("Present")
            holder.t_status.setTextColor(context.getResources().getColor(R.color.dot_dark_screen2))
        }
        else if((" "+userList.get(position).status.toString()).toLowerCase().trim().equals("a"))
        {
            holder.t_status.setText("Absent")
            holder.t_status.setTextColor(context.getResources().getColor(R.color.dot_dark_screen1))
        }
        else if((" "+userList.get(position).status.toString()).toLowerCase().trim().equals("l"))
        {
            holder.t_status.setText("Leave")
            holder.t_status.setTextColor(context.getResources().getColor(R.color.dot_light_screen1))
        }
        else
        {
            holder.t_status.setText(" "+userList.get(position).status)
        }

//        holder.t_enddate.setText("created_at:- "+userList.get(position).createdAt+"\n Name:- "+userList.get(position).firstName+" "+userList.get(position).lastName)




     /*   Picasso.with(context).load("http://vidhyalaya.co.in/sspanel/"+userList.get(position).notificationimage)
            .error(R.drawable.bell)
            .into(holder.image1)*/


    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var t_date=itemView.findViewById<TextView>(R.id.t_date)
        var t_status=itemView.findViewById<TextView>(R.id.t_status)
    }
}