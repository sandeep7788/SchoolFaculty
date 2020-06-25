package com.education.Faculty.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dating.schoolfaculty.R
import com.education.Faculty.API.Data
import com.education.Faculty.API.Faculty.Homeworkapus
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeworkAdapter(var context: Context, val userList: ArrayList<Homeworkapus>) : RecyclerView.Adapter<HomeworkAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_homeworkadapter, parent, false)





        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val startdate =
            formateDateFromstring("dd/MM/yyyy", "dd, MMM yyyy",(userList.get(position).submissionDate+" ").trim())
/*        val enddate =
            formateDateFromstring("dd/MM/yyyy", "dd, MMM yyyy",(userList.get(position).endDate+" ").trim())
        Log.e("@@adapter",userList.get(position).id)*/
        Picasso.with(context).load("http://skoolstarr.com/sspanel/"+userList.get(position).filePath+" ")
            .error(R.drawable.school)
            .into(holder.image)

        holder.title.setText(userList.get(position).title+" ")
        holder.description.setText(userList.get(position).description+" ")
        holder.classe.setText("Class :- "+userList.get(position).class_+" ")
        holder.date.setText(startdate)
        holder.section.setText("Section :- "+userList.get(position).section)

//        holder.t_enddate.setText("Start Date:- "+startdate+" | End Date "+enddate)

        var a = (userList.get(position).isactive).trim().toInt()

        if(a==1)
        {
            holder.card_view.setBackgroundColor(context.getResources().getColor(R.color.tg))
        }
        else if(a==0)
        {
            holder.card_view.setBackgroundColor(context.getResources().getColor(R.color.tr))
        }
        else
        {
            holder.card_view.setBackgroundColor(context.getResources().getColor(R.color.to))
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image=itemView.findViewById<ImageView>(R.id.thumbnail)
        var title=itemView.findViewById<TextView>(R.id.title)
        var description=itemView.findViewById<TextView>(R.id.description1)
        var classe=itemView.findViewById<TextView>(R.id.classe)
        var date=itemView.findViewById<TextView>(R.id.date)
        var section=itemView.findViewById<TextView>(R.id.section)
        var card_view=itemView.findViewById<View>(R.id.view)
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