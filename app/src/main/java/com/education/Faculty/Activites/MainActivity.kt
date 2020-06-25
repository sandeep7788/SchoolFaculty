package com.education.Faculty.Activites

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dating.schoolfaculty.R
import com.education.Faculty.API.Data
import com.education.Faculty.API.Faculty.UserLogin
import com.education.Faculty.API.Services
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{
    lateinit var e1:EditText
    lateinit var e2:EditText
    lateinit var pb: AlertDialog
    lateinit var sv:ScrollView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        e1=findViewById(R.id.e1)
        e2=findViewById(R.id.e2)
        sv=findViewById(R.id.sv)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        pb=SpotsDialog.Builder().setContext(this@MainActivity).build()
        pb.setMessage("Connecting to Server")

            var textView = findViewById<TextView>(R.id.textView)
                .setOnClickListener {
                    startActivity(
                        Intent(
                            this,
                            Dashbord::class.java
                        )
                    )
                }




            var b1 = findViewById<Button>(R.id.button)
                .setOnClickListener {

                    if (e1.text.isEmpty()) {
                        e1.setError("Invalid")
                    } else if (e2.text.isEmpty()) {
                        e2.setError("Invalid Password")
                    } else if (!e1.text.isEmpty() && !e2.text.isEmpty()) {
                        pb!!.show()
//                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                        hideTheKeyboard(this@MainActivity,e2)
                        login1()
                    }
                }
    }

    fun hideTheKeyboard(context: Context, editText: EditText) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            editText.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }

    fun login1()
    {
        var preferences= Services()
        val loginResponseCall: Call<List<UserLogin>> =
            preferences.getServices()!!.getProducts(e1.text.toString(),e2.text.toString(),"test123")
        Log.e("@@l",e1.text.toString()+""+e2.text.toString())
        loginResponseCall.enqueue(object : Callback<List<UserLogin>>
        {
            override fun onFailure(call: Call<List<UserLogin>>, t: Throwable) {
                pb!!.dismiss()
                Log.e("@@l",t.message)
                Toast.makeText(this@MainActivity,"Username or Password incorrect or check network connection",Toast.LENGTH_LONG).show()
            }

            @SuppressLint("ResourceAsColor")
            override fun onResponse(
                call: Call<List<UserLogin>>,
                response: Response<List<UserLogin>>
            ) {

                pb.dismiss()
                Log.e("@@l",response.message())
                if (response.isSuccessful) {
                    setdata()
                    setdata(Data.number,e1.text.toString())
                    setdata(Data.password,e2.text.toString())
                    Toast.makeText(this@MainActivity, "Login Successfully", Toast.LENGTH_LONG).show()
                    setdata(Data.Userid,(response.body()!!.get(0).userid+" ").trim())
                    setdata(Data.logintest,(response.body()!!.get(0).login+" ").trim())
                    setdata(Data.name,(response.body()!!.get(0).name+" ").trim())
                    setdata(Data.Facultyid,(response.body()!!.get(0).facultyid+" ").trim())
                    setdata(Data.image,(response.body()!!.get(0).image+" ").trim())
                    setdata(Data.Classteacher,(response.body()!!.get(0).classTeacher+" ").trim())
                    setdata(Data.school_id,(response.body()!!.get(0).schoolId+" ").trim())
                    setdata(Data.school_name,(response.body()!!.get(0).schoolName+" ").trim())
                    setdata(Data.schoolSession,(response.body()!!.get(0).schoolSession+" ").trim())
                    startActivity(Intent(this@MainActivity,Dashbord::class.java))
               /*     Log.e("@@data",response.body()!!.get(0).userid+response.body()!!.get(0).login+response.body()!!.get(0).name+
                            response.body()!!.get(0).image
                    +response.body()!!.get(0).classTeacher
                    +response.body()!!.get(0).schoolId
                    +response.body()!!.get(0).schoolName
                    +response.body()!!.get(0).schoolSession)*/

                }
            }
        })
    }



    fun setdata()
    {

        val sharedPreferences: SharedPreferences = getSharedPreferences("v1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()

        editor.putString("id","id")
        editor.putString(Data.Userid," ....")
        editor.putString("classid"," ....")
        editor.putString("classe"," ....")
        editor.putString("Student"," ....")
        editor.putString("student_image"," ....")
        editor.putString("school_id"," ....")
        editor.putString("school_name"," ....")
        editor.putString("staffid"," ....")
        editor.putString("staffuserid"," ....")
        editor.putString("Classteacher"," ....")
        editor.putString(Data.number," ....")
        editor.putString(Data.image," ....")
        editor.apply()
        editor.commit()
    }



    fun alertdilog()
    {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it)
        }


        builder!!.setMessage("Are you sure you want to Exit ?")
            .setTitle("Exit")

        builder.apply {
            setPositiveButton("No") { dialog, id ->
                val selectedId = id
                dialog.dismiss()
            }
            setNegativeButton("Yes") { dialog, id ->
                val selectedId = id
                finish()
            }
        }
        val dialog: AlertDialog? = builder.create()

        dialog!!.show()
    }

    override fun onBackPressed() {
        alertdilog()
    }

    fun setdata(key: String, value: String) {
        try {
            if((value.trim()).equals("null"))
            {
                val sharedPreferences: SharedPreferences =
                    getSharedPreferences("v1", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(key,"....")
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

    fun getdata(key: String): String {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "d_null")
        return value!!
    }override fun onDestroy() {
    if (pb != null && pb.isShowing()) {
        pb.dismiss()
    }
    this!!.onVisibleBehindCanceled()
    super.onDestroy()
}
}