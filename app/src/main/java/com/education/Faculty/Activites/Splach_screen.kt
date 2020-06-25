package com.education.Faculty.Activites

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.education.Faculty.API.Data
import com.education.Faculty.API.Services
import com.education.Faculty.API.User_List
import com.dating.schoolfaculty.R
import com.education.Faculty.API.Faculty.UserLogin
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Splach_screen : AppCompatActivity() {
    lateinit var pb: AlertDialog
    var userdata=ArrayList<User_List>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)
        pb= SpotsDialog.Builder().setContext(this@Splach_screen).build()
        pb.setMessage("Connecting to Server")

        userdata= ArrayList()
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, WelcomeActivity::class.java))

            // close this activity
            finish()
        }, 3400)
    }

    fun login1(number:String,password:String)
    {
        var preferences= Services()
        val loginResponseCall: Call<List<UserLogin>> =
            preferences.getServices()!!.getProducts(number,password,"test123")
        Log.e("@@l",number+" "+password)
        loginResponseCall.enqueue(object : Callback<List<UserLogin>>
        {
            override fun onFailure(call: Call<List<UserLogin>>, t: Throwable) {
                pb!!.dismiss()
                Log.e("@@l",t.message)
                Toast.makeText(this@Splach_screen,"Username or Password incorrect or check network connection",
                    Toast.LENGTH_LONG).show()
            }

            @SuppressLint("ResourceAsColor")
            override fun onResponse(
                call: Call<List<UserLogin>>,
                response: Response<List<UserLogin>>
            ) {
                Log.e("@@l",response.message())
                if (response.isSuccessful) {

                    Toast.makeText(this@Splach_screen, "Login Successfully", Toast.LENGTH_LONG).show()
                    setdata(Data.Userid,(response.body()!!.get(0).userid+" ").trim())
                    setdata(Data.logintest,(response.body()!!.get(0).login+" ").trim())
                    setdata(Data.name,(response.body()!!.get(0).name+" ").trim())
                    setdata(Data.Facultyid,(response.body()!!.get(0).facultyid+" ").trim())
                    setdata(Data.image,(response.body()!!.get(0).image+" ").trim())
                    setdata(Data.Classteacher,(response.body()!!.get(0).classTeacher+" ").trim())
                    setdata(Data.school_id,(response.body()!!.get(0).schoolId+" ").trim())
                    setdata(Data.school_name,(response.body()!!.get(0).schoolName+" ").trim())
                    setdata(Data.schoolSession,(response.body()!!.get(0).schoolSession+" ").trim())
                    pb.dismiss()
                    startActivity(Intent(this@Splach_screen,Dashbord::class.java))
 /*                        Log.e("@@data",response.body()!!.get(0).userid+response.body()!!.get(0).login+response.body()!!.get(0).name+
                                 response.body()!!.get(0).image
                         +response.body()!!.get(0).classTeacher
                         +response.body()!!.get(0).schoolId
                         +response.body()!!.get(0).schoolName
                         +response.body()!!.get(0).schoolSession)*/

                }
            }
        })
    }

    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "d_null")
        return value
    }
    override fun onDestroy() {

        this!!.onVisibleBehindCanceled()
        super.onDestroy()
    }
    fun setdata(key: String, value: String) {
        try {
            if((value.trim()).equals("null"))
            {
                val sharedPreferences: SharedPreferences =
                    getSharedPreferences("v1", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(key," ....")
                editor.apply()
                editor.commit()
            }
            else {
                val sharedPreferences: SharedPreferences =
                    getSharedPreferences("v1", Context.MODE_PRIVATE)
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
