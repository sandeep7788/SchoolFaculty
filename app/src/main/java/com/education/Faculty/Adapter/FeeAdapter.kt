package com.education.Faculty.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.education.Faculty.API.Feesdet
import com.dating.schoolfaculty.R
import java.util.*

class FeeAdapter(var context: Context, val userList: ArrayList<Feesdet>) : RecyclerView.Adapter<FeeAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fee_adapter, parent, false)




        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        Log.e("@@adapter"," "+userList.get(position).amt)
        holder.t_date.setText(" "+userList.get(position).duedate)
        holder.t_title.setText(" "+userList.get(position).feehead)
        holder.t_des.setText(" "+userList.get(position).amt)
        holder.t_number.setText((position+1).toString())
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var t_date=itemView.findViewById<TextView>(R.id.t_date)
        var t_title=itemView.findViewById<TextView>(R.id.t_title)
        var t_des=itemView.findViewById<TextView>(R.id.t_des)
        var t_number=itemView.findViewById<TextView>(R.id.t_number)
        var cv=itemView.findViewById<LinearLayout>(R.id.l1)
    }
}