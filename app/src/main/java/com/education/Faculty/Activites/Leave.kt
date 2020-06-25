package com.education.Faculty.Activites

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.education.Faculty.API.Data
import com.education.Faculty.API.Profileapi
import com.education.Faculty.API.Services
import com.dating.schoolfaculty.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class Leave : AppCompatActivity() {

    var cal = Calendar.getInstance()
    lateinit var i_startdate: ImageView
    lateinit var i_enddate: ImageView
    lateinit var startdate: DatePicker
    lateinit var enddate: DatePicker
    lateinit var t_startdate: TextView
    lateinit var t_enddate: TextView
    lateinit var e_reason:EditText
    lateinit var e_description:EditText
    var progress: AlertDialog? = null
    lateinit var button:Button
    lateinit var imageview:CircleImageView
    lateinit var t_name:TextView
    lateinit var i_backbutton:ImageView
    lateinit var i_reason:ImageView
    lateinit var i_description:ImageView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        i_startdate = findViewById(R.id.i_startdate)
        i_enddate = findViewById(R.id.i_enddate)
        startdate = findViewById(R.id.datePicker1)
        enddate = findViewById(R.id.datePicker2)
        t_startdate = findViewById(R.id.t_startdate)
        t_enddate = findViewById(R.id.t_enddate)
        e_reason=findViewById(R.id.e_reason)
        e_description=findViewById(R.id.e_description)
        button=findViewById(R.id.button)
        imageview=findViewById(R.id.imageview)
        t_name=findViewById(R.id.t_name)
        i_backbutton=findViewById(R.id.i_backbutton)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        i_reason=findViewById(R.id.i_reason)
        i_description=findViewById(R.id.i_description)
        progress = SpotsDialog.Builder().setContext(this@Leave).build()
        progress!!.setTitle("Connecting to Server")
        progress!!.setMessage("loading")
        progress!!.setCancelable(false)

        Picasso.with(this@Leave).load("http://skoolstarr.com/sspanel/"+getdata(Data.image))
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(imageview)

        i_backbutton.setOnClickListener {    finish() }

        t_name.setText(getdata(Data.name))

        i_reason.setOnClickListener { openkeybord(e_reason) }
        i_description.setOnClickListener { openkeybord(e_description) }

        button.setOnClickListener {
            if(e_reason.text.isEmpty())
            {
                e_reason.setError("Enter Valid Reason")
            }
            else if(e_description.text.isEmpty())
            {
                e_description.setError("Enter Valie Description")
            }
            else if(t_startdate.text.isEmpty())
            {
                t_startdate.setError("Select Date")
            }
            else if(t_enddate.text.isEmpty())
            {
                t_enddate.setError("Select Date")
            }
            else {
                load_user()
            }

        }

        val dateSetListener1 = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(t_startdate)
            }
        }

        i_startdate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@Leave,
                    dateSetListener1,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })

        val dateSetListener2 = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(t_enddate)
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        i_enddate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@Leave,
                    dateSetListener2,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })

    }

    private fun updateDateInView(t: TextView) {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        t!!.text = sdf.format(cal.getTime())
    }

    fun getdata(key:String): String? {
        val sharedPreferences: SharedPreferences = this@Leave!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val  data: SharedPreferences =  sharedPreferences
        var value = data.getString(key,"defoult_value")
        return value
    }
    fun load_user() {


        progress!!.show()
        var preferences = Services()
        val loginResponseCall: Call<Profileapi> =
            preferences.getLeaveServices()!!.getLandingPageReport(getdata(Data.school_id),getdata(Data.Userid),e_reason.text.toString(),e_description.text.toString(),t_startdate.text.toString(),t_enddate.text.toString())!!
        loginResponseCall.enqueue(object : Callback<Profileapi> {
            override fun onFailure(call: Call<Profileapi>, t: Throwable) {
                Log.e("@@",t.message)
                progress!!.dismiss()
                Toast.makeText(this@Leave,"Try Later or Error genrate",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Profileapi>, response: Response<Profileapi>) {
                Log.e("@@",response.body()!!.email)
                progress!!.dismiss()
                Toast.makeText(this@Leave,response!!.body()!!.email,Toast.LENGTH_LONG).show()
            }


        })
    }

    override fun onDestroy() {
        if (progress != null && progress!!.isShowing()) {
            progress!!.dismiss()
        }
        super.onDestroy()
    }
    fun openkeybord(edittext:EditText)
    {
        Log.e("@@", "On Keyboard Button click event!")
        edittext.requestFocus();
        edittext.editableText
        edittext.setFocusableInTouchMode(true);
        val imm = edittext.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN, 0)
        imm?.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT)
    }
}