package com.education.Vidhyalaya_Faculty_APP.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.Notifactionpojo
import com.education.Vidhyalaya_Faculty_APP.R
import java.util.*

class NotifactionAdapter(var context: Context, val userList: ArrayList<Notifactionpojo>) : RecyclerView.Adapter<NotifactionAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_notification, parent, false)








        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.e("@@adapter",userList.get(position).id)
        holder.t_title.setText(userList.get(position).title)
        holder.t_des.setText(userList.get(position).description)
        holder.t_enddate.setText("created_at:- "+userList.get(position).createdAt)
//        +"\n Created by :- "+userList.get(position).+""

    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var t_title=itemView.findViewById<TextView>(R.id.t_title)
        var t_des=itemView.findViewById<TextView>(R.id.t_des)
        var t_enddate=itemView.findViewById<TextView>(R.id.t_enddate)
        var image1=itemView.findViewById<ImageView>(R.id.image1)
    }
}