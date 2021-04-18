package com.education.Vidhyalaya_Faculty_APP.Activites

import android.app.AlertDialog
import android.content.*
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.education.Vidhyalaya_Faculty_APP.API.*
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.Classlist
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.Section
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.Studentlistapi
import com.education.Vidhyalaya_Faculty_APP.Adapter.FeeAdapter
import com.education.Vidhyalaya_Faculty_APP.Adapter.MyListAdapterSection
import com.education.Vidhyalaya_Faculty_APP.R
import com.education.Vidhyalaya_Faculty_APP.helper.GetSchoolCessionModel
import com.education.Vidhyalaya_Faculty_APP.helper.Utlity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Attendence : AppCompatActivity(), MyListener {
    lateinit var progressDialog: android.app.AlertDialog
    var Classlist = ArrayList<Classlist>()
    var Section = ArrayList<Section>()
    var Subject = ArrayList<String>()
    var Absentstudent = ArrayList<String>()
    var leave_student = ArrayList<String>()
    lateinit var friends: ArrayList<Studentlistapi>
    lateinit var list: ListView
    lateinit var cv: CardView
    lateinit var itemAtPos: String
    var classid = 0
    var section_id: Int = 0
    lateinit var rv: RecyclerView
    lateinit var i_backbutton1: ImageView
    lateinit var t_section: TextView
    var i = 0
    lateinit var b_studentslist: Button
    lateinit var l1: LinearLayout
    var status: Boolean = false
    lateinit var imageUri: Uri
    private val PICK_IMAGE: Int = 100
    lateinit var b_submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setContentView(R.layout.activity_attendence)
        cv = findViewById(R.id.cv)
        list = findViewById(R.id.list)
        rv = findViewById(R.id.rv)
        Classlist = ArrayList()
        Section = ArrayList()
        leave_student = ArrayList()
        friends = ArrayList()
        Absentstudent = ArrayList()
        progressDialog = SpotsDialog.Builder().setContext(this@Attendence).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("Please wait....")
        progressDialog.setCancelable(false)
        i_backbutton1 = findViewById(R.id.i_backbutton1)
        t_section = findViewById(R.id.t_section)
        b_studentslist = findViewById(R.id.b_studentslist)
        b_submit = findViewById(R.id.b_submit)

        var MyReceiver: BroadcastReceiver? = null;
        MyReceiver = com.education.vidhyalaya.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        val i_closew: ImageView = findViewById(R.id.i_closew)
        i_closew.setOnClickListener { cv.visibility = View.GONE }

        val imageview: CircleImageView = findViewById(R.id.imageview)
        Picasso.with(this@Attendence).load("http://vidhyalaya.co.in/sspanel/" + getdata(Data.image))
            .error(R.drawable.school)
            .into(imageview)
        val t_name: TextView = findViewById(R.id.t_name)
        t_name.setText(getdata(Data.name))

        val b_submit: Button = findViewById(R.id.b_submit)
        b_submit.setOnClickListener {
            getSchoolCession()
        }

        val b_studentlist: Button = findViewById(R.id.b_studentlist1)
        b_studentlist.setOnClickListener {
            Log.e("@@", "1")
            b_submit.visibility = View.GONE
            if (classid == 0) {
                Log.e("@@", "2")
                Toast.makeText(this@Attendence, "Please Select Any Class", Toast.LENGTH_LONG).show()

            } else {
                Section.clear()
                Classlist.clear()
                Subject.clear()
                studentlist()
            }
        }

        i_backbutton1.setOnClickListener {
            var intent = Intent(
                this@Attendence,
                Dashbord::class.java
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        var b_class: Button = findViewById(R.id.b_class)
        b_class.setOnClickListener {

            cv.visibility = View.VISIBLE
            Section.clear()
            Classlist.clear()
            Subject.clear()
            getclasslist()
            var t_class = findViewById<TextView>(R.id.t_class)
            t_class.setText("")
            t_class.setHint("_")
            t_section.setText("")
            t_section.setHint("_")
            var t: TextView = findViewById(R.id.t_class)
            list.setOnItemClickListener() { adapterView, view, position, id ->
                itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
//
                cv.visibility = View.GONE
                t.setText("Class :- " + Classlist.get(itemIdAtPos.toInt()).class_ + "")

                var t_section: TextView = findViewById(R.id.t_section)
                t_section.setText("")
                section_id = 0

                classid = (Classlist.get(itemIdAtPos.toInt()).id + " ").trim().toInt()
                if (classid == 0) {

                    Toast.makeText(this@Attendence, "Please Select Any Class", Toast.LENGTH_LONG)
                        .show()

                } else {
                    Section.clear()
                    Classlist.clear()
                    Subject.clear()
                    studentlist()
                }
            }
        }
        var b_section: Button = findViewById(R.id.b_section)
        b_section.setOnClickListener {
            cv.visibility = View.VISIBLE
            sectione()
            var t: TextView = findViewById(R.id.t_section)
            list.setOnItemClickListener() { adapterView, view, position, id ->
                itemAtPos = (adapterView.getItemAtPosition(position)).toString()
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)

                cv.visibility = View.GONE
                t.setText("Section :- " + Section.get(position).section + "")
                section_id = Section.get(position).id.toInt()

                if (classid == 0) {

                    Toast.makeText(
                        this@Attendence,
                        "Please Select Any Class",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    Section.clear()
                    Classlist.clear()
                    Subject.clear()
                    studentlist()
                }
            }
        }

    }

    @NonNull
    private fun createPartFromString(value: String): RequestBody? {
        return RequestBody.create(
            MultipartBody.FORM, value
        )
    }

    fun getclasslist() {
        Section.clear()
        Subject.clear()
        list.setAdapter(null)
        progressDialog.show()
        var preferences = Services()
        val loginResponseCall: Call<List<Classlist>> =
            preferences.getclass()!!.getdata(getdata(Data.school_id))
        Classlist.clear()
        loginResponseCall.enqueue(object : Callback<List<Classlist>> {
            override fun onFailure(call: Call<List<Classlist>>, t: Throwable) {
                Log.e("@@e", "error")
                Toast.makeText(this@Attendence, "Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()

                b_submit.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Classlist>>,
                response: Response<List<Classlist>>
            ) {

                progressDialog.dismiss()
                Toast.makeText(this@Attendence, "wait", Toast.LENGTH_SHORT)
                if (response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(
                            this@Attendence,
                            "No Data Found Try Later",
                            Toast.LENGTH_LONG
                        ).show()
                        b_submit.visibility = View.GONE
                        cv.visibility = View.GONE

                    } else {


                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Classlist>()
                            Classlist.addAll(listOf(response.body()!!.get(i)))

                        }

                        val myListAdapter = MyListAdapterclass(this@Attendence, Classlist)
                        list.adapter = myListAdapter
                        myListAdapter.notifyDataSetChanged()
                        myListAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@Attendence, "Don't Have Any Data", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()

                    b_submit.visibility = View.GONE
                    cv.visibility = View.GONE
                }
            }
        })
    }


    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "_")
        return value
    }

    fun takeLast(value: String?, count: Int): String? {
        if (value == null || value.trim { it <= ' ' }.length == 0 || count < 1) {
            return ""
        }
        return if (value.length > count) {
            value.substring(value.length - count)
        } else {
            value
        }
    }

    fun studentlist() {


        progressDialog.show()
        friends.clear()
        var preferences = Services()
        val loginResponseCall: Call<List<Studentlistapi>> =
            preferences.getstudentlist()!!.getdata(
                getdata(Data.school_id),
                classid.toString(),
                section_id,
                getdata(Data.schoolSession)
            )

        loginResponseCall.enqueue(object : Callback<List<Studentlistapi>> {
            override fun onFailure(call: Call<List<Studentlistapi>>, t: Throwable) {
                Log.e("@@studentlist", t.message!!)
                progressDialog.dismiss()
                cv.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<List<Studentlistapi>>,
                response: Response<List<Studentlistapi>>
            ) {
                progressDialog.dismiss()
                if (response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(this@Attendence, "There are no children available in the section of this class", Toast.LENGTH_SHORT).show()

                    } else if (response != null) {
                        for (i in 0 until response.body()!!.size) {
                            Log.e("@@userlist", response.body()!!.get(i).id)
                            mutableListOf<Holadaypojo>()
                            friends.addAll(listOf(response.body()!!.get(i)))
                        }
                        var adapter = FeeAdapter(
                            this@Attendence.applicationContext,
                            friends, this@Attendence
                        )
                        rv.setBackgroundColor(Color.parseColor("#442990"))
                        rv.layoutManager = GridLayoutManager(this@Attendence, 1)
                        rv.isNestedScrollingEnabled = false
                        rv.setItemViewCacheSize(45)
                        rv.isDrawingCacheEnabled = true
                        rv.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        rv.adapter = adapter
                        var b_studentlist: Button = findViewById(R.id.b_studentlist1)
                        b_studentlist.visibility = View.GONE
                        b_submit.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(this@Attendence, "Don't Have Any Data", Toast.LENGTH_LONG).show()

                    cv.visibility = View.GONE
                }
            }
        })
    }

    fun sectione() {
            friends.clear()
            var adapter = FeeAdapter(
                this@Attendence.applicationContext,
                friends, this@Attendence
            )
            rv.adapter = adapter
            adapter.notifyDataSetChanged()

        progressDialog.show()
        var preferences = Services()
        val loginResponseCall: Call<List<Section>> =
            preferences.getsectionlist()!!.getdata(getdata(Data.school_id))


        loginResponseCall.enqueue(object : Callback<List<Section>> {
            override fun onFailure(call: Call<List<Section>>, t: Throwable) {
                Log.e("@@e", "error")
                Toast.makeText(this@Attendence, "Problem", Toast.LENGTH_SHORT)
                progressDialog.dismiss()
                cv.visibility = View.GONE

            }

            override fun onResponse(
                call: Call<List<Section>>,
                response: Response<List<Section>>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@Attendence, "wait", Toast.LENGTH_SHORT)
                if (response.isSuccessful) {
                    Log.e("@@size", response.body()!!.size.toString())
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(
                            this@Attendence,
                            "No Data Found Try Later",
                            Toast.LENGTH_LONG
                        ).show()
                        cv.visibility = View.GONE
                    } else {
                        Section.clear()
                        Classlist.clear()
                        Subject.clear()
                        friends.clear()
                        for (i in 0 until response.body()!!.size) {
                            Section.addAll(listOf(response.body()!!.get(i)))
                        }

                        val myListAdapter = MyListAdapterSection(this@Attendence, Section)
                        list.adapter = myListAdapter

                    }
                } else {
                    Toast.makeText(this@Attendence, "Don't Have Any Data", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                    cv.visibility = View.GONE

                }
            }
        })
    }

    fun submitdata(session: String) {
        progressDialog.show()
        var preferences = Services()
        Log.e("@@", Absentstudent.toString() + " \n@@" + leave_student.toString() + " ")
        val c: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate: String = df.format(c)


        val result1: String = Absentstudent.toString().replace("[", "")
        var resultabsent: String = result1.toString().replace("]", "")
        val result2: String = leave_student.toString().replace("[", "")
        var resultleave: String = result2.toString().replace("]", "")

        if (resultabsent.equals("") || resultabsent == null) {
            resultabsent = "0"
        }
        if (resultleave.equals("") || resultleave == null) {
            resultleave = "0"
        }

        Log.e("@@absent ", resultabsent + "_")
        Log.e("@@leave ", resultleave + "_")

        val loginResponseCall: Call<Profileupdateapi> =
            preferences.insertAttendence()!!.getdata(
                getdata(Data.school_id), getdata(Data.Facultyid), formattedDate.toString(),
                classid.toString(), "" + t_section.text.toString(),
                resultabsent.toString().trim(),
                resultleave.toString().trim(),
                getdata(Data.schoolSession),
                session
            )
        Log.e("@@", loginResponseCall.toString() + " ")
        loginResponseCall.enqueue(object : Callback<Profileupdateapi> {
            override fun onFailure(call: Call<Profileupdateapi>, t: Throwable) {
                Log.e("@@studentlist", t.message!!)
                progressDialog.dismiss()
                Toast.makeText(
                    this@Attendence,
                    "Don't Have Any Data" + t.message!!.toString(),
                    Toast.LENGTH_LONG
                ).show()
                cv.visibility = View.GONE
                Utlity.showErrorDialgo(this@Attendence)

            }

            override fun onResponse(
                call: Call<Profileupdateapi>,
                response: Response<Profileupdateapi>
            ) {
                Log.e("@@", response.toString() + " ")
                progressDialog.dismiss()

                ok()
                Toast.makeText(
                    this@Attendence,
                    " " + response.body()?.status.toString() + " ",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun getSchoolCession() {

        var preferences = Services()
        val loginResponseCall: Call<List<GetSchoolCessionModel>> =
            preferences.get_school_cession()!!.getSchoolCession1(
                getdata(Data.school_id)!!.toInt()
            )

        loginResponseCall.enqueue(object : Callback<List<GetSchoolCessionModel>> {
            override fun onFailure(call: Call<List<GetSchoolCessionModel>>, t: Throwable) {
                Utlity.showErrorDialgo(this@Attendence)
            }

            override fun onResponse(
                call: Call<List<GetSchoolCessionModel>>,
                response: Response<List<GetSchoolCessionModel>>
            ) {
                if (response.isSuccessful) {

                    Log.e(">>", "onResponse: " + response.body()!!.get(0).session)

                    submitdata(response.body()!!.get(0).session)
                } else {
                    Utlity.showErrorDialgo(this@Attendence)
                }
            }

        }
        )
    }

    override fun onDestroy() {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        this@Attendence.onVisibleBehindCanceled()
        super.onDestroy()
    }

    override fun callback1(view: View?, result: String?) {
        Log.e("@@clicked1", result.toString() + "")

        if (!Absentstudent.toString().contains(result.toString() + "")) {
            leave_student.remove(result.toString() + "")
            Absentstudent.add(result.toString() + "")
        }

    }

    override fun callback2(view: View?, result: String?) {
        Log.e("@@clicked2", result.toString() + "")

        if (!leave_student.toString().contains(result.toString() + "")) {
            Absentstudent.remove(result.toString() + "")
            leave_student.add(result.toString() + "")
        }

        Log.e("@@data", Absentstudent.toString())
        for (i in 0 until Absentstudent.size) {
            Log.e("@@dataAbsentstudent", Absentstudent.get(i).toString())
        }
        for (i in 0 until leave_student.size) {
            Log.e("@@dataleave_student", leave_student.get(i).toString())
        }
    }

    override fun callback3(view: View?, result: String?) {

        Absentstudent.remove(result.toString() + "")
        leave_student.remove(result.toString() + "")
        Log.e("@@clicked3", result.toString())

    }

    fun ok() {
        Utlity.showDialog(
            this@Attendence,
            "Your request submitted Successfully!",
            SweetAlertDialog.SUCCESS_TYPE
        )
    }

    fun dialog() {
        val builder =
            AlertDialog.Builder(this@Attendence, AlertDialog.THEME_HOLO_LIGHT)
        builder.setTitle("Need Permission")
        builder.setMessage("This app needs storage permission.")
        builder.setPositiveButton("Grant", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0?.cancel()
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        })
        builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0!!.cancel()
            }

        })
        builder.show()
    }
}
/*
* home work scroll
* answer sheet wrong student
* */