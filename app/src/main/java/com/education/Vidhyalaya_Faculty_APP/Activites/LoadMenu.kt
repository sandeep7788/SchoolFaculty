package com.education.Vidhyalaya_Faculty_APP.Activites

import android.content.*
import android.net.ConnectivityManager
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
import com.education.Vidhyalaya_Faculty_APP.API.Data
import com.education.Vidhyalaya_Faculty_APP.API.Holadaypojo
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.MomentApi
import com.education.Vidhyalaya_Faculty_APP.API.Services
import com.education.Vidhyalaya_Faculty_APP.R
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.Homeworkapus
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.answerapi
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.videoapi
import com.education.Vidhyalaya_Faculty_APP.Adapter.*
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadMenu : AppCompatActivity() {

    lateinit var progressDialog: android.app.AlertDialog
    lateinit var img1: ImageView
    lateinit var name: TextView
    lateinit var info: TextView
    lateinit var rv: RecyclerView
    lateinit var back_button:ImageView
    lateinit var sync:ImageView
    var menudata=ArrayList<MomentApi>()
    var holidaydata=ArrayList<Holadaypojo>()
    var homeworkdata=ArrayList<Homeworkapus>()
    var videodata=ArrayList<videoapi>()
    var data="0"
    lateinit var imageView8:ImageView
    lateinit var i_addmoment:ImageView
    lateinit var i_addhomework:ImageView
    lateinit var i_addanswersheet:ImageView
    lateinit var i_video:ImageView
    var answerdata=ArrayList<answerapi>()
    lateinit var textView2:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_menu)
        rv=findViewById(R.id.rv)
        back_button=findViewById(R.id.imageView4)
        sync=findViewById(R.id.imageView6)
        imageView8=findViewById(R.id.imageView8)
        i_addmoment=findViewById(R.id.i_addmoment)
        i_addhomework=findViewById(R.id.i_addhomework)
        i_addanswersheet=findViewById(R.id.i_addanswersheet)
        i_video=findViewById(R.id.i_video)
        textView2=findViewById(R.id.textView2)
        menudata= ArrayList()
        holidaydata= ArrayList()
        homeworkdata= ArrayList()
        videodata= ArrayList()
        answerdata= ArrayList()
        progressDialog= SpotsDialog.Builder().setContext(this@LoadMenu).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("Please wait....")
        progressDialog.setCancelable(false)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        var MyReceiver: BroadcastReceiver?= null;
        MyReceiver = com.education.vidhyalaya.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        back_button.setOnClickListener {
            var intent= Intent(this,
                Dashbord::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        i_addmoment.setOnClickListener {
            startActivity(Intent(this@LoadMenu,AddMoment::class.java))
        }

        i_addhomework.setOnClickListener {
            startActivity(Intent(this@LoadMenu,Addhomework::class.java))
        }
        i_addanswersheet.setOnClickListener {
            startActivity(Intent(this@LoadMenu,AddAnswersheet::class.java))
        }
        i_video.setOnClickListener {
            startActivity(Intent(this@LoadMenu,Addvideo::class.java))
        }

        var intent=intent
        data= intent.getStringExtra("name").toString()
        sync.setOnClickListener {
            when(data)
        {
            "moment" -> moment()
            "homework" -> homework()
            "answersheet" -> answeresheet()
//            "location" -> moment()
//            "help" -> moment()
//            "ebook" -> moment()
//            "quiz" -> moment()
            "holiday"->holiday()
            "video"->Videolist()
            else
            -> Toast.makeText(this@LoadMenu,"Something Wrong",Toast.LENGTH_LONG).show()
        }
        }
        Log.e("@@data",data+" ");
        when(data)
        {

            "moment" -> moment()
            "homework" -> homework()
            "answersheet" -> answeresheet()
//            "location" -> moment()
//            "help" -> moment()
//            "ebook" -> moment()
//            "quiz" -> moment()
            "holiday"->holiday()
            "video"->Videolist()
            else
                -> Toast.makeText(this@LoadMenu,"Try Later",Toast.LENGTH_LONG).show()
        }
    }

    fun moment()
    {
        textView2.setText("Moments")
        i_addmoment.visibility=View.VISIBLE
        Log.e("@@l",getdata(Data.school_id)+" "+getdata(Data.Userid))
        menudata.clear()
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<MomentApi>> =
            preferences.get_dashbord_menu()!!.getmenu(getdata(Data.school_id),getdata(Data.Facultyid))
        loginResponseCall.enqueue(object : Callback<List<MomentApi>>
        {

            override fun onFailure(call: Call<List<MomentApi>>, t: Throwable) {
                Log.e("@@userlist",t.message!!)
                Toast.makeText(this@LoadMenu, "Check Your Connection Or Try Later", Toast.LENGTH_LONG).show()
                imageView8.visibility=View.VISIBLE
                    if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()

                }
            }

            override fun onResponse(
                call: Call<List<MomentApi>>,
                response: Response<List<MomentApi>>
            ) {
                menudata.clear()
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                if(response.isSuccessful) {
                    imageView8.visibility=View.INVISIBLE
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(this@LoadMenu, "No Data Found Try Later", Toast.LENGTH_LONG)
                            .show()
                        imageView8.visibility=View.VISIBLE
                    } else {
                        Log.e("@@userlist", response.body().toString())
                        Log.e("@@userlist", response.body().toString())
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<MomentApi>()
                            menudata.addAll(listOf(response.body()!!.get(i)))
                        }
                        var adapter = DashbordMenuAdapter(
                            this@LoadMenu!!.applicationContext,
                            menudata
                        )

                        rv.layoutManager = GridLayoutManager(this@LoadMenu, 1)
                        rv.setNestedScrollingEnabled(false)
                        rv.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
                else
                {
                    Toast.makeText(this@LoadMenu,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                    imageView8.visibility=View.VISIBLE
                }
            }
        })
    }
    fun getdata(key:String): String? {
        val sharedPreferences: SharedPreferences = this!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val  data: SharedPreferences =  sharedPreferences
        var value = data.getString(key,"_")
        return value
    }

    fun homework()
    {
        i_addhomework.visibility=View.VISIBLE
        textView2.setText("Home Work")
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<Homeworkapus>> =
            preferences.gethomeworkservices()!!.gethomework(getdata(Data.school_id),getdata(
                Data.Facultyid))

        Log.e("@@",getdata(Data.school_id)+" "+getdata(Data.Facultyid))

        loginResponseCall.enqueue(object : Callback<List<Homeworkapus>>
        {
            override fun onFailure(call: Call<List<Homeworkapus>>, t: Throwable) {
                Log.e("@@homework",t.message!!)
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                Toast.makeText(this@LoadMenu,"Try Later",Toast.LENGTH_LONG).show()
                imageView8.visibility=View.VISIBLE
            }

            override fun onResponse(
                call: Call<List<Homeworkapus>>, response: Response<List<Homeworkapus>>) {

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                homeworkdata.clear()

                if((response.body()).toString().trim().equals("[]"))
                {
                    Toast.makeText(this@LoadMenu,"No Data Found Try Later",Toast.LENGTH_LONG).show()
                    imageView8.visibility=View.VISIBLE
                }
                else
                {
                    Toast.makeText(this@LoadMenu,"Please wait",Toast.LENGTH_LONG).show()
                    if(response.isSuccessful) {
                        imageView8.visibility=View.INVISIBLE
                        Log.e("@@size", response.body()!!.size.toString())
                        if((response.body()).toString().trim().equals("[]"))
                        {
                            Toast.makeText(this@LoadMenu,"No Data Found Try Later",Toast.LENGTH_LONG).show()
                            imageView8.visibility=View.VISIBLE
                        }
                        else if (response != null) {
                            for (i in 0 until response.body()!!.size) {
                                Log.e("@@userlist", response.body()!!.get(i).id)
                                mutableListOf<Homeworkapus>()
                                homeworkdata.addAll(listOf(response.body()!!.get(i)))
                            }

                            var adapter = HomeworkAdapter(
                                this@LoadMenu!!.applicationContext,
                                homeworkdata
                            )
                            rv.setBackgroundColor(R.color.app!!)
                            rv.layoutManager = GridLayoutManager(this@LoadMenu, 1)
                            rv.setNestedScrollingEnabled(false)
                            rv.adapter = adapter
                            adapter.notifyDataSetChanged()
                        }
                    }
                    else
                    {
                        Toast.makeText(this@LoadMenu,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                        imageView8.visibility=View.VISIBLE
                    }

                }

            }
        })
    }

    fun holiday()
    {
        textView2.setText("Holidays")
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<Holadaypojo>> =
            //preferences.getUserliost()!!.getUserlist(e1.text.toString(),section)
            preferences.getholidayservices()!!.getholiday(getdata(Data.school_id))
        loginResponseCall.enqueue(object : Callback<List<Holadaypojo>>
        {
            override fun onFailure(call: Call<List<Holadaypojo>>, t: Throwable) {
                Log.e("@@homework",t.message!!)
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                imageView8.visibility=View.VISIBLE
                Toast.makeText(this@LoadMenu,"Try Later",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Holadaypojo>>,response: Response<List<Holadaypojo>>) {

                Log.e("@@data", "onResponse: "+response.body()!!.get(0).createdAt )
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                holidaydata.clear()
                if(response.isSuccessful) {
                    imageView8.visibility=View.INVISIBLE
                    Log.e("@@size", response.body()!!.size.toString())
                    if((response.body()).toString().trim().equals("[]"))
                    {
                        Toast.makeText(this@LoadMenu,"No Data Found Try Later",Toast.LENGTH_LONG).show()
                        imageView8.visibility=View.VISIBLE
                    }
                    else if (response != null) {
                        for (i in 0 until response.body()!!.size) {
                            Log.e("@@userlist", response.body()!!.get(i).id)
                            mutableListOf<Holadaypojo>()
                            holidaydata.addAll(listOf(response.body()!!.get(i)))
                        }
                        var adapter = HolidayAdapter(
                            this@LoadMenu!!.applicationContext,
                            holidaydata
                        )
                        rv.setBackgroundColor(R.color.app!!)
                        rv.layoutManager = GridLayoutManager(this@LoadMenu, 1)
                        rv.setNestedScrollingEnabled(false)
                        rv.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
                else
                {
                    Toast.makeText(this@LoadMenu,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                    imageView8.visibility=View.VISIBLE
                }

            }
        })
    }

    fun answeresheet()
    {
        textView2.setText("Answer Sheet")
        i_addanswersheet.visibility=View.VISIBLE
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<answerapi>> =
            //preferences.getUserliost()!!.getUserlist(e1.text.toString(),section)
            preferences.getanswersheetservices()!!.getanswersheet(getdata(Data.school_id),getdata(
                Data.Facultyid))

        loginResponseCall.enqueue(object : Callback<List<answerapi>>
        {
            override fun onFailure(call: Call<List<answerapi>>, t: Throwable) {
                Log.e("@@homework",t.message!!)
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                Toast.makeText(this@LoadMenu,"Try Later",Toast.LENGTH_LONG).show()
                imageView8.visibility=View.VISIBLE
            }

            override fun onResponse(
                call: Call<List<answerapi>>, response: Response<List<answerapi>>) {

                answerdata.clear()
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                if(response.isSuccessful) {
                    imageView8.visibility=View.INVISIBLE
                    Log.e("@@size", response.body()!!.size.toString())
                    if((response.body()).toString().trim().equals("[]"))
                    {
                        Toast.makeText(this@LoadMenu,"No Data Found Try Later",Toast.LENGTH_LONG).show()
                        imageView8.visibility=View.VISIBLE
                    }
                    else
                    {
                        Toast.makeText(this@LoadMenu,"Please wait", Toast.LENGTH_LONG).show()
                        if(response.isSuccessful) {
                            imageView8.visibility=View.INVISIBLE


                            Log.e("@@size", response.body()!!.size.toString())

                            for (i in 0 until response.body()!!.size) {
                                Log.e("@@userlist", response!!.body().toString())
                                mutableListOf<answerapi>()
                                answerdata.addAll(listOf(response.body()!!.get(i)))
                            }
                            var adapter = AnswerAdapter(
                                this@LoadMenu!!.applicationContext,
                                answerdata
                            )
                            adapter.notifyDataSetChanged()
                            rv.setBackgroundColor(R.color.app!!)
                            rv.layoutManager = GridLayoutManager(this@LoadMenu, 1)
                            rv.setNestedScrollingEnabled(false)
                            rv.adapter = adapter
                            adapter.notifyDataSetChanged()
                        }
                    }

                }
                else
                {
                    Toast.makeText(this@LoadMenu,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                    imageView8.visibility=View.VISIBLE
                }
            }

        })
    }

    fun Videolist()
    {
        i_video.visibility=View.VISIBLE
        rv.getRecycledViewPool().clear()
        textView2.setText("Video Gallery")
        videodata.clear()
//        progressDialog.show()
//        var preferences= Services()
        /*val loginResponseCall: Call<List<videoapi>> =
            //preferences.getUserliost()!!.getUserlist(e1.text.toString(),section)
            preferences.getvideolist()!!.getvideolist(getdata(Data.school_id)!!.trim()!!.toInt(),
                getdata(Data.id)!!.trim()!!.toInt(),getdata(Data.classid)!!.trim()!!.toInt(),0)
        loginResponseCall.enqueue(object : Callback<List<videoapi>>
        {
            override fun onFailure(call: Call<List<videoapi>>, t: Throwable) {
                Log.e("@@homework",t.message!!.toString()+" ")
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                imageView8.visibility=View.VISIBLE
                Toast.makeText(this@LoadMenu,"Try Later",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<videoapi>>,response: Response<List<videoapi>>) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                if(response.isSuccessful) {
                    imageView8.visibility=View.INVISIBLE
                    Log.e("@@size", response.body()!!.size.toString())
                    if((response.body()).toString().trim().equals("[]"))
                    {
                        Toast.makeText(this@LoadMenu,"No Data Found Try Later",Toast.LENGTH_LONG).show()
                        imageView8.visibility=View.VISIBLE
                    }
                    else if (response != null) {
                        for (i in 0 until response.body()!!.size) {
                            Log.e("@@userlist", response.body()!!.get(i).id)
                            mutableListOf<videoapi>()
                            videodata.addAll(listOf(response.body()!!.get(i)))
                        }
                        var adapter = VideoAdapter(
                            this@LoadMenu!!.applicationContext,
                            videodata
                        )
                        adapter.notifyDataSetChanged()
                        rv.setBackgroundColor(R.color.app!!)
                        rv.layoutManager = GridLayoutManager(this@LoadMenu, 1)
                        rv.setNestedScrollingEnabled(false);
                        rv.setItemViewCacheSize(45);
                        rv.setDrawingCacheEnabled(true);
                        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        rv.adapter = adapter

                    }
                }
                else
                {
                    Toast.makeText(this@LoadMenu,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                    imageView8.visibility=View.VISIBLE
                }
            }

        })
*/    }

    override fun onDestroy() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss()
        }
        this!!.onVisibleBehindCanceled()
        super.onDestroy()
    }

    override fun onBackPressed() {
     /*   var intent= Intent(this@LoadMenu,
            Dashbord::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)*/
        var intent= Intent(this@LoadMenu,
            Dashbord::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        super.onBackPressed()
    }
}