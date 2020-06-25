package com.education.Faculty.Activites

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dating.schoolfaculty.R
import com.education.Faculty.API.*
import com.education.Faculty.Adapter.AttendenceAdapter
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class Attendence : AppCompatActivity() {
    lateinit var pb: android.app.AlertDialog
    lateinit var rv:RecyclerView
    lateinit var t_totalday:TextView
    lateinit var t_persentday:TextView
    lateinit var i_backbutton: ImageView
    lateinit var imageView6:ImageView
    lateinit var t_teachername:TextView
    lateinit var imageview: CircleImageView
    lateinit var name:TextView
    var userdata=ArrayList<AttendanceList>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhomework)
        t_totalday=findViewById(R.id.t_totalday)
        t_persentday=findViewById(R.id.t_persentday)
        i_backbutton=findViewById(R.id.i_backbutton)
        imageView6=findViewById(R.id.imageView6)
        t_teachername=findViewById(R.id.t_teachername)
        imageview=findViewById(R.id.imageview)
        name=findViewById(R.id.t_name)
        rv=findViewById(R.id.rv)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        pb= SpotsDialog.Builder().setContext(this@Attendence).build()
        pb!!.setTitle("Connecting to Server")
        pb!!.setMessage("Please Wait")
        pb.setCancelable(false)
        userdata= ArrayList()
        Attendencedata1()

        Picasso.with(this@Attendence).load("http://skoolstarr.com/sspanel/"+getdata(Data.image))
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(imageview)
        name.setText(getdata(Data.name))

        i_backbutton.setOnClickListener { finish() }
        imageView6.setOnClickListener { Attendencedata()
            Attendencedata1()
        }
    }

    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "d_null")
        return value
    }
//holiday date
//    attence show error
//    null no data found
//    fees table

    fun Attendencedata()
    {
        val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())

        val currentYear: Int = calendar.get(Calendar.YEAR)
        val currentMonth: Int = calendar.get(Calendar.MONTH) + 1
        val currentDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

        Toast.makeText(
            this,
            "Today's Date: $currentYear$currentMonth$currentDay",
            Toast.LENGTH_SHORT
        ).show()
        userdata.clear()
        pb.show()
        var preferences = Services()
        val loginResponseCall: Call<Attendentapi> =
            preferences.getAttendenceList()!!.get_atendenceList(getdata(Data.school_id)!!.trim(),getdata(Data.id)!!.trim(),(currentMonth).toString(),(currentYear).toString())
        loginResponseCall.enqueue(object : Callback<Attendentapi>
        {
            override fun onFailure(call: Call<Attendentapi>, t: Throwable) {
                pb.dismiss()
                Log.e("@@attendence",t.message)
                Toast.makeText(this@Attendence,"Failure"+t.message, Toast.LENGTH_LONG).show()
            }
            override fun onResponse(
                call: Call<Attendentapi>,
                response: Response<Attendentapi>
            ) {

                pb.dismiss()
                if (response.isSuccessful) {

                    if (response != null) {
                        for (i in 0 until response.body()!!.attendanceList!!.size) {
                            mutableListOf<Holadaypojo>()
                            userdata.addAll(listOf(response.body()!!.attendanceList.get(i)))
                        }
                        var adapter = AttendenceAdapter(
                            this@Attendence!!.applicationContext,
                            userdata
                        )
                        rv.setBackgroundColor(R.color.app!!)
                        rv.layoutManager = GridLayoutManager(this@Attendence, 1)
                        rv.setNestedScrollingEnabled(false);
                        rv.setItemViewCacheSize(45);
                        rv.setDrawingCacheEnabled(true);
                        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        rv.adapter = adapter

                    }

                } else {
                    Toast.makeText(this@Attendence,"Try Again or Sync",Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    fun Attendencedata1()
    {
        val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())

        val currentYear: Int = calendar.get(Calendar.YEAR)
        val currentMonth: Int = calendar.get(Calendar.MONTH) + 1
        val currentDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

        Toast.makeText(
            this,
            "Today's Date: $currentYear    $currentMonth    $currentDay",
            Toast.LENGTH_SHORT
        ).show()

        pb.show()
        var preferences = Services()
        val loginResponseCall: Call<Attendentapi> =
            preferences.getAttendenceList()!!.get_atendenceList(getdata(Data.school_id)!!.trim()!!,getdata(Data.id)!!.trim()!!,(currentMonth).toString(),(currentYear).toString())

        loginResponseCall.enqueue(object : Callback<Attendentapi>
        {
            override fun onFailure(call: Call<Attendentapi>, t: Throwable) {
                pb.dismiss()
                Log.e("@@attendence",t.message)
                Toast.makeText(this@Attendence,"Failure "+t.message +"Sync Again", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(
                call: Call<Attendentapi>,
                response: Response<Attendentapi>
            ) {
                pb.dismiss()
                if (response.isSuccessful) {

                    t_totalday.setText(response.body()!!.totalDays+" ")
                    t_persentday.setText(response.body()!!.totalAttendance +" ")
                    t_teachername.setText("Teacher :- " +response.body()!!.teacherName)
                    Attendencedata()
                } else {
                    Toast.makeText(this@Attendence,"Try Again or Sync",Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    override fun onDestroy() {
        if (pb != null && pb.isShowing()) {
            pb.dismiss()
        }
        this@Attendence!!.onVisibleBehindCanceled()
        super.onDestroy()
    }
}