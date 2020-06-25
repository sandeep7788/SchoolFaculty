package com.education.Faculty.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.education.Faculty.API.Data
import com.education.Faculty.API.User_List
import com.education.Faculty.Activites.Dashbord
import com.dating.schoolfaculty.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class UserAdapter(var context: Context,val userList: ArrayList<User_List>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {




    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_adapter, parent, false)








        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        Log.e("@@adapter",userList.get(position).id)
        holder.name.setText(userList.get(position).student)
        holder.classe.setText("class:- "+userList.get(position).class_)
        holder.school.setText(userList.get(position).schoolName)



        Picasso.with(context).load("http://skoolstarr.com/sspanel/"+userList[position].studentImage)
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(holder.img)

        holder.cv.setOnClickListener {

            /*setdata(
                    userList.get(position).id,userList.get(position).userid,userList.get(position).classid,userList.get(position).class_,userList.get(position).section,
                    userList.get(position).student,userList.get(position).studentImage,userList.get(position).schoolId,userList.get(position).schoolName,userList.get(position).staffid,userList.get(position).staffuserid,
                    userList.get(position).classteacher,userList.get(position).facultyPhone)*/



            Log.e("@@", userList.get(position)?.section + " ")

            setdata(Data.id, userList.get(position).id + " ")
            setdata(Data.Userid, userList.get(position).userid + " ")
            setdata(Data.classid, userList.get(position).classid + " ")
            setdata(Data.classe, userList.get(position).class_ + " ")
            setdata(Data.section, userList.get(position).section + " ")
            setdata(Data.name, userList.get(position).student + " ")
            setdata(Data.image, userList.get(position).studentImage + " ")
            setdata(Data.school_id, userList.get(position).schoolId + " ")
            setdata(Data.school_name, userList.get(position).schoolName + " ")
            setdata(Data.staffid, userList.get(position).staffid + " ")
            setdata(Data.staffuserid, userList.get(position).staffuserid + " ")
            setdata(Data.Classteacher, userList.get(position).classteacher + " ")

            context.startActivity(
                Intent(
                    context,
                    Dashbord::class.java
                )
            )
            (context as Activity).finish()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name=itemView.findViewById<TextView>(R.id.t_name)
        var img=itemView.findViewById<CircleImageView>(R.id.image)
        var classe =itemView.findViewById<TextView>(R.id.t_class)
        var school=itemView.findViewById<TextView>(R.id.t_school)
        var cv=itemView.findViewById<CardView>(R.id.cv)
    }

    fun setdata(id:String,Userid:String,classid:String,classe:String,section:String,Student:String,student_image:String,
                school_id:String,school_name:String,staffid:String,staffuserid:String,Classteacher:String,FacultyPhone:String)
    {

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("id",id)
        editor.putString("Userid",Userid)
        editor.putString("classid",classid)
        editor.putString("classe",classe)
        editor.putString("section",section)
        editor.putString("Student",Student)
        editor.putString("student_image",student_image)
        editor.putString("school_id",school_id)
        editor.putString("school_name",school_name)
        editor.putString("staffid",staffid)
        editor.putString("staffuserid",staffuserid)
        editor.putString("Classteacher",Classteacher)
        editor.putString("FacultyPhone",FacultyPhone)
        editor.apply()
        editor.commit()
    }

    fun setdata(key: String, value: String) {
        try {
            if((value.trim()).equals("null"))
            {
                val sharedPreferences: SharedPreferences =
                    context!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(key,"....")
                editor.apply()
                editor.commit()
            }
            else {
                val sharedPreferences: SharedPreferences =
                    context!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(key, value)
                editor.apply()
                editor.commit()
            }
        }
        catch (e: Exception)
        {
            System.out.println("@@e"+e.message)
        }

    }
}