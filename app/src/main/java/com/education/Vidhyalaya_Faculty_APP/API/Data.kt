package com.education.Vidhyalaya_Faculty_APP.API

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast

class Data: Application()
{
    val sharedPreferences: SharedPreferences = this.getSharedPreferences("v1", Context.MODE_PRIVATE)
    private val minstance: Data? = null
    private val instance: Data? = null

    companion object
    {
        var id="id"
        var Userid="Userid"
        var classid="classid"
        var classe="classe"
        var section="section"
        var name="name"
        var image="headerimage"
        var school_id="school_id"
        var school_name="school_name"
        var staffid="staffid"
        var staffuserid="staffuserid"
        var Classteacher="Classteacher"
        var Facultyid="Facultyid"
        var schoolSession="schoolSession"
        var number="number"
        var login="login"
        var logintest="logintest"
        var password="password"
        var loginstatus="loginstatus"
    }


/*    "id": "35271",
    "Userid": "34635",
    "classid": "706",
    "class": "LKG",
    "section": "86",
    "Student": "VANDANA  SONI",
    "student_image": "upload/profile/IMG_20190801_200532.jpg",
    "school_id": "28",
    "school_name": "Sanskar A Real Education English Medium School",
    "staffid": "465",
    "staffuserid": "42873",
    "Classteacher": "RAMA   VYAS",
    "FacultyPhone": "6350088908"*/

    @Synchronized
    fun getInstance(context: Context?): Data? {
        if (minstance == null) {
            Log.e("@@darta","null")
        }
        return minstance
    }

    fun setdata(id:String,Userid:String,classid:String,classe:String,section:String,Student:String,student_image:String,
                school_id:String,school_name:String,staffid:String,staffuserid:String,Classteacher:String,FacultyPhone:String)
    {
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("id",id)
        editor.putString("Userid",Userid)
        editor.putString("classid",classid)
        editor.putString("class",classe)
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

    fun setdata(key:String,value:String)
    {
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
        editor.commit()
    }
    fun getdata(key:String): String? {
        val  data: SharedPreferences =  sharedPreferences
        var value = data.getString(key,"_")
        return value
    }
    fun getdemo(context:Context)
    {
        Toast.makeText(this,"toast",Toast.LENGTH_LONG).show()
    }
    fun getInstance(): Data? {
        return instance
    }
    fun setInfo(a:String)
    {
//        (this@Dashbord as Data).getdemo() //.getdata(Data.school_name)
        Log.e("@@",a)
    }
}