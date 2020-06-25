package com.education.Faculty.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.education.Faculty.API.Holadaypojo
import com.dating.schoolfaculty.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HolidayAdapter(var context: Context, val userList: ArrayList<Holadaypojo>) : RecyclerView.Adapter<HolidayAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_holiday, parent, false)


//holiday
//awsedrftgyhujikol
//
//
//
//
//
//


        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val startdate =
            formateDateFromstring("dd/MM/yyyy", "dd, MMM yyyy",(userList.get(position).startDate+" ").trim())
        val enddate =
            formateDateFromstring("dd/MM/yyyy", "dd, MMM yyyy",(userList.get(position).endDate+" ").trim())
        Log.e("@@adapter",userList.get(position).id)
        holder.t_sdate.setText(startdate)
        holder.t_edate.setText(enddate)
        holder.t_title.setText(userList.get(position).title)
        holder.t_des.setText(userList.get(position).description)

//        holder.t_enddate.setText("Start Date:- "+startdate+" | End Date "+enddate)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var t_sdate=itemView.findViewById<TextView>(R.id.t_sdate)
        var t_edate=itemView.findViewById<TextView>(R.id.t_edate)
        var t_title=itemView.findViewById<TextView>(R.id.t_title)
        var t_des=itemView.findViewById<TextView>(R.id.t_des)
        var t_enddate=itemView.findViewById<TextView>(R.id.t_enddate)
    }

    fun formateDateFromstring(
        inputFormat: String?,
        outputFormat: String?,
        inputDate: String?
    ): String? {
        var parsed: Date? = null
        var outputDate = ""
        val df_input =
            SimpleDateFormat(inputFormat, Locale.getDefault())
        val df_output =
            SimpleDateFormat(outputFormat, Locale.getDefault())
        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)
        } catch (e: ParseException) {
            Log.e("@@e", "ParseException - dateFormat"+e.message.toString())
        }
        return outputDate
    }
}