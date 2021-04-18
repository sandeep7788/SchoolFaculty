package com.education.Vidhyalaya_Faculty_APP.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.education.Vidhyalaya_Faculty_APP.R
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.videoapi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class VideoAdapter(var context: Context, val userList: ArrayList<videoapi>) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.videoadapter, parent, false)


        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val enddate =
            formateDateFromstring("dd/MM/yyyy", "dd, MMM yyyy",(userList.get(position).createdAt+" ").trim())
        Log.e("@@adapter",userList.get(position).id)
        holder.t_enddate.setText("Created at:- "+userList.get(position).createdAt)
        holder.t_title.setText(userList.get(position).title)
        holder.t_des.setText(userList.get(position).description)

        val dashUrl = "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1491561340/hello_cuwgcb.mp4"

//        XmlPullParserException(dashUrl)
        holder.l_video.setOnClickListener {

/*
            if((userList.get(position).filePath+" ")!=null)
            {

                var intent= Intent(context,
                    Video::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("url","http://vidhyalaya.co.in/sspanel/"+userList.get(position).filePath+"")
                context.startActivity(intent)
            }
            else
            {
                Toast.makeText(context,"Try Later This Video",Toast.LENGTH_LONG).show()
            }
*/
        }
//        holder.t_enddate.setText("Start Date:- "+startdate+" | End Date "+enddate)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var t_title=itemView.findViewById<TextView>(R.id.t_title)
        var t_des=itemView.findViewById<TextView>(R.id.t_des)
        var t_enddate=itemView.findViewById<TextView>(R.id.t_enddate)
        var l_video=itemView.findViewById<LinearLayout>(R.id.l_video)
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