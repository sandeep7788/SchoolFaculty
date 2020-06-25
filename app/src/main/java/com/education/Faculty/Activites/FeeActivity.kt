package com.education.Faculty.Activites

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.education.Faculty.API.Feesdet
import com.dating.schoolfaculty.R
import dmax.dialog.SpotsDialog

class FeeActivity : AppCompatActivity() {
    lateinit var progressDialog: android.app.AlertDialog
    lateinit var img1: ImageView
    lateinit var name: TextView
    lateinit var info: TextView
    lateinit var rv: RecyclerView
    lateinit var back_button: ImageView
    lateinit var sync: ImageView
    var menudata=ArrayList<Feesdet>()
    lateinit var imageView5: ImageView
    var data="0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fee)
        rv=findViewById(R.id.rv)
        back_button=findViewById(R.id.imageView4)
        sync=findViewById(R.id.imageView6)
        imageView5=findViewById(R.id.imageView5)
        menudata= ArrayList()
        progressDialog= SpotsDialog.Builder().setContext(this@FeeActivity).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)


        back_button.setOnClickListener {
        finish()
        }

        sync.setOnClickListener {  }

    }

    fun getdata(key:String): String? {
        val sharedPreferences: SharedPreferences = this!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val  data: SharedPreferences =  sharedPreferences
        var value = data.getString(key,"defoult_value")
        return value
    }override fun onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss()
        }
        this!!.onVisibleBehindCanceled()
        super.onDestroy()
    }
}