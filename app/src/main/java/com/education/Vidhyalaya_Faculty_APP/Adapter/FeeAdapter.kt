package com.education.Vidhyalaya_Faculty_APP.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.education.Vidhyalaya_Faculty_APP.R
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.Studentlistapi
import com.education.Vidhyalaya_Faculty_APP.API.MyListener
import java.util.*


class FeeAdapter(var context: Context, val userList: ArrayList<Studentlistapi>,var listener: MyListener) : RecyclerView.Adapter<FeeAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fee_adapter, parent, false)


        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.e("@@adapter"," "+userList.get(position).id)
        holder.t_name.setText(" "+userList.get(position).student)
//        holder.t_title.setText(" "+userList.get(position).student)
//        holder.t_des.setText(" "+userList.get(position).student)
//        holder.t_number.setText((position+1).toString())
        holder.r_a.setOnClickListener {
            listener!!.callback1(holder.r_a,userList.get(position).id+" ");
        }
        holder.r_l.setOnClickListener {
            listener!!.callback2(holder.r_l,userList.get(position).id+" ");
        }
        holder.r_c.setOnClickListener {
            listener!!.callback3(holder.r_l,userList.get(position).id+" ");
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var t_name=itemView.findViewById<TextView>(R.id.t_name)
        var r_a=itemView.findViewById<RadioButton>(R.id.r_a)
        var r_l=itemView.findViewById<RadioButton>(R.id.r_l)
        var r_c=itemView.findViewById<RadioButton>(R.id.r_c)
        var cv=itemView.findViewById<LinearLayout>(R.id.l1)
    }
}