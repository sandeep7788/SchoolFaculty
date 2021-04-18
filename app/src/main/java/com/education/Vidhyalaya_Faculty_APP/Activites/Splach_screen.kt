package com.education.Vidhyalaya_Faculty_APP.Activites

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
import com.education.Vidhyalaya_Faculty_APP.API.Data
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.UserLogin
import com.education.Vidhyalaya_Faculty_APP.API.Services
import com.education.Vidhyalaya_Faculty_APP.API.User_List
import com.education.Vidhyalaya_Faculty_APP.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Splach_screen : AppCompatActivity() {
    lateinit var pb: AlertDialog
    var userdata=ArrayList<User_List>()
    var token=" "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)

        pb= SpotsDialog.Builder().setContext(this@Splach_screen).build()
        pb.setMessage("Please Wait....")
        userdata= ArrayList()
        FirebaseApp.initializeApp(this)





        if(getdata(Data.loginstatus).equals("1"))
        {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("@@", "Fetching FCM registration token failed", task.exception)
                    login1(getdata(Data.number).toString(),getdata(Data.password).toString(),token)
                    return@OnCompleteListener
                } else {
                    // Get new FCM registration token
                    token = task.result.toString()
                    Log.e("@@>>", token.toString())
                    login1(getdata(Data.number).toString(),getdata(Data.password).toString(),token)
                }
            })
        }
        else
        {
            setdata(Data.loginstatus,"0")
            Handler().postDelayed({

                startActivity(Intent(this, MainActivity::class.java))

                finish()
            }, 3400)
        }
    }

    fun login1(number:String,password:String,token:String)
    {
        pb.show()
        var preferences= Services()
        val loginResponseCall: Call<List<UserLogin>> =
            preferences.getServices()!!.getProducts(number,password,token)
        Log.e("@@l",number+" "+password+" _ "+token)
        loginResponseCall.enqueue(object : Callback<List<UserLogin>>
        {
            override fun onFailure(call: Call<List<UserLogin>>, t: Throwable) {
                pb!!.dismiss()
                Log.e("@@l",t.message!!)
                Toast.makeText(this@Splach_screen,"Username or Password incorrect or check network connection",
                    Toast.LENGTH_LONG).show()
                goToLogin()
            }
            @SuppressLint("ResourceAsColor")
            override fun onResponse(
                call: Call<List<UserLogin>>,
                response: Response<List<UserLogin>>
            ) {
                pb.dismiss()
                Log.e("@@l", response.body()?.size.toString())
                if (response.isSuccessful) {

                    if(response.body()?.size!! >0) {


                        setdata(Data.loginstatus,"1")

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
                    startActivity(Intent(this@Splach_screen,Dashbord::class.java))
                    finish()

                    }
                    else {
                        goToLogin()
                    }
                }
                else {
                    goToLogin()
                }
            }
        })
    }
    fun goToLogin() {
        startActivity(Intent(this@Splach_screen,MainActivity::class.java))
        finish()
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