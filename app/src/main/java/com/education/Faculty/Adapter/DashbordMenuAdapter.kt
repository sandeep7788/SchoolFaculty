package com.education.Faculty.Adapter

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.education.Faculty.API.Faculty.MomentApi
import com.dating.schoolfaculty.R
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class DashbordMenuAdapter(var context: Context, val userList: ArrayList<MomentApi>) : RecyclerView.Adapter<DashbordMenuAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    var MULTIPLE_PERMISSIONS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_dashbordmenu, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(userList.get(position).title + "\n \n" + userList.get(position).description)
        Picasso.with(context).load("http://skoolstarr.com/sspanel/" + userList[position].filePath)
            .error(R.drawable.ic_sync_black_24dp)
            .resize(290, 290)
            .into(holder.img)

        holder.downloadDoc.setOnClickListener {


            downloadFile("http://skoolstarr.com/sspanel/" + userList.get(position).filePath)

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name = itemView.findViewById<TextView>(R.id.textview)
        var img = itemView.findViewById<ImageView>(R.id.t_date)
        var cv = itemView.findViewById<CardView>(R.id.cv)
        var downloadDoc = itemView.findViewById<Button>(R.id.button)
    }

/*    fun setdata(
        id: String,
        Userid: String,
        classid: String,
        classe: String,
        section: String,
        Student: String,
        student_image: String,
        school_id: String,
        school_name: String,
        staffid: String,
        staffuserid: String,
        Classteacher: String,
        FacultyPhone: String
    ) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("id", id)
        editor.putString("Userid", Userid)
        editor.putString("classid", classid)
        editor.putString("classe", classe)
        editor.putString("section", section)
        editor.putString("Student", Student)
        editor.putString("student_image", student_image)
        editor.putString("school_id", school_id)
        editor.putString("school_name", school_name)
        editor.putString("staffid", staffid)
        editor.putString("staffuserid", staffuserid)
        editor.putString("Classteacher", Classteacher)
        editor.putString("FacultyPhone", FacultyPhone)
        editor.apply()
        editor.commit()
    }*/


    fun saveImage(img: ImageView) {
        var outputStream: OutputStream? = null
        val drawable = img.getDrawable() as BitmapDrawable
        val bitmap = drawable.bitmap
        val filepath = Environment.getExternalStorageDirectory()
        val dir = File(filepath.absolutePath + R.string.app_name)
        dir.mkdir()
        val file =
            File(dir, System.currentTimeMillis().toString() + ".jpg")
        try {
            outputStream = FileOutputStream(file)
        } catch (e: java.lang.Exception) {
            Toast.makeText(context, "error " + e.message, Toast.LENGTH_SHORT).show()
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        Toast.makeText(
            context,
            "Saved Image \n Check in File Directory \n storage/" + R.string.app_name + "/....",
            Toast.LENGTH_SHORT
        ).show()
        try {
            outputStream!!.flush()
        } catch (e: IOException) {
            Toast.makeText(context, "" + e.message, Toast.LENGTH_SHORT).show()
        }
        try {
            outputStream!!.close()
        } catch (e: IOException) {
            Toast.makeText(context, "" + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun downloadFile(uRl: String?) {
        val random = Random().nextInt(10000 - 1000) + 1000
        val direct = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/AnhsirkDasarp"
        )
        if (!direct.exists()) {
            direct.mkdirs()
        }
        val mgr =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(uRl)
        val request = DownloadManager.Request(
            downloadUri
        )
        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI
                    or DownloadManager.Request.NETWORK_MOBILE
        )
            .setAllowedOverRoaming(false).setTitle("Downloding")
            .setDescription("Student Pictures")
            .setDestinationInExternalPublicDir("/File", "$random.jpg")
        mgr.enqueue(request)

        // Open Download Manager to view File progress
        Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show()
        var intent=Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun permission_storage() {
        val permissionCheckStorage = ContextCompat.checkSelfPermission(
            context!!.applicationContext,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (permissionCheckStorage == PackageManager.PERMISSION_GRANTED && permissionCheckStorage == PackageManager.PERMISSION_GRANTED) {

            true

        } else if (permissionCheckStorage != PackageManager.PERMISSION_GRANTED && permissionCheckStorage != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(
                context!!.applicationContext as Activity,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_PHONE_STATE
                ),
                MULTIPLE_PERMISSIONS
            )
            false


        }
    }
}