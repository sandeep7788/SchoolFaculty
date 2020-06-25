package com.education.Faculty.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.education.Faculty.API.Faculty.Notifactionpojo
import com.dating.schoolfaculty.R
import com.squareup.picasso.Picasso
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
        holder.t_enddate.setText("created_at:- "+userList.get(position).createdAt+"\n Created by :- "+userList.get(position).createdBy+"")

        if((userList.get(position).isactive).trim().toString().equals("1"))
        {
            holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen2))
        }
        else if((userList.get(position).isactive).trim().toString().equals("0"))
        {
            holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.dot_dark_screen1))
        }
        else
        {
            holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.dot_light_screen1))
        }


/*        Picasso.with(context).load("http://skoolstarr.com/sspanel/"+userList.get(position).notificationimage)
            .error(R.drawable.bell)
            .into(holder.image1)*/






    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var t_title=itemView.findViewById<TextView>(R.id.t_title)
        var t_des=itemView.findViewById<TextView>(R.id.t_des)
        var t_enddate=itemView.findViewById<TextView>(R.id.t_enddate)
        var cv=itemView.findViewById<CardView>(R.id.cv)
        var image1=itemView.findViewById<ImageView>(R.id.image1)
    }
}