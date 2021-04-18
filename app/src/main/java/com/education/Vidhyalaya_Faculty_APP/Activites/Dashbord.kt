package com.education.Vidhyalaya_Faculty_APP.Activites

import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.education.Vidhyalaya_Faculty_APP.API.Data
import com.education.Vidhyalaya_Faculty_APP.Adapter.MyAdapter
import com.education.Vidhyalaya_Faculty_APP.R
import com.github.javiersantos.appupdater.AppUpdater
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class Dashbord : AppCompatActivity()
{
    var RECORD_REQUEST_CODE = 101
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    private val instance: Dashbord? = null
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var user_img:CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbord)
        mDrawerLayout=findViewById(R.id.drawer_layout)
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)
        user_img=findViewById(R.id.i_image)

        tabLayout!!.addTab(tabLayout!!.newTab().setIcon(R.drawable.ic_home_black_24dp))
        tabLayout!!.addTab(tabLayout!!.newTab().setIcon(R.drawable.ic_chat_black_24dp))
        tabLayout!!.addTab(tabLayout!!.newTab().setIcon(R.drawable.ic_account_circle_black_24dp))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
//        this.setRetainInstance(true);


        val adapter = MyAdapter(
            this,
            supportFragmentManager,
            tabLayout!!.tabCount
        )
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        Picasso.with(this@Dashbord).load("http://vidhyalaya.co.in/sspanel/"+getdata(Data.image))
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(user_img)

        var t_name=findViewById<TextView>(R.id.t_name)
        t_name.setText(getdata(Data.name))

        makerequest()

        var MyReceiver: BroadcastReceiver?= null;
        MyReceiver = com.education.vidhyalaya.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        var l_homework=findViewById<LinearLayout>(R.id.l_homework)
            .setOnClickListener {
                var intent= Intent(this@Dashbord,
                    LoadMenu::class.java)

                intent.putExtra("name","homework")
                startActivity(intent) }

        var l_holiday=findViewById<LinearLayout>(R.id.l_holiday)
            .setOnClickListener {
                var intent= Intent(this@Dashbord,
                    LoadMenu::class.java)

                intent.putExtra("name","holiday")
                startActivity(intent) }

        var l_answersheet=findViewById<LinearLayout>(R.id.l_answersheet)
            .setOnClickListener {
                var intent= Intent(this@Dashbord,
                    LoadMenu::class.java)

                intent.putExtra("name","answersheet")
                startActivity(intent) }

        var l_feereport=findViewById<LinearLayout>(R.id.l_feereport)
            .setOnClickListener {
                var intent= Intent(this@Dashbord,
                    LoadMenu::class.java)

                intent.putExtra("name","moment")
                startActivity(intent) }

        var l_location=findViewById<LinearLayout>(R.id.l_location)
            .setOnClickListener {
                var intent= Intent(this@Dashbord,
                    LoadMenu::class.java)

                intent.putExtra("name","location")
                startActivity(intent) }

        var l_help=findViewById<LinearLayout>(R.id.l_help)
            .setOnClickListener {
                var intent= Intent(this@Dashbord,
                    HelpPage::class.java)
                startActivity(intent) }

        var l_moment=findViewById<LinearLayout>(R.id.l_moment)
            .setOnClickListener {
                var intent= Intent(this@Dashbord,
                    LoadMenu::class.java)

                intent.putExtra("name","moment")
                startActivity(intent) }




        var l_leave=findViewById<LinearLayout>(R.id.l_leave)
            .setOnClickListener {
                var intent=Intent(this@Dashbord,
                    Leave::class.java)

                startActivity(intent) }

        var l_changepassword=findViewById<LinearLayout>(R.id.l_changepassword)
            .setOnClickListener {
                var intent=Intent(this@Dashbord,
                    ChangePassword::class.java)
                startActivity(intent) }

        var l_attendence=findViewById<LinearLayout>(R.id.l_attendence)
            .setOnClickListener {
                var intent=Intent(this@Dashbord,
                    Attendence::class.java)
                startActivity(intent) }


        var l_logout=findViewById<LinearLayout>(R.id.l_logout)
            .setOnClickListener {
                alertdilog1()
            }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        try {
            val appUpdater = AppUpdater(this)
            appUpdater.start()
            AppUpdater(this)
                .setTitleOnUpdateAvailable("Update available")
                .setContentOnUpdateAvailable("Check out the latest version available of my app!")
                .setTitleOnUpdateNotAvailable("Update not available")
                .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
                .setButtonUpdate("Update now?")
                .setCancelable(false)


        } catch (e:Exception) {

        }

    }
    fun open_navigation_drawable()
    {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }
    fun getInstance(): Dashbord? {
        return instance
    }
    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            this@Dashbord!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "_")
        return value
    }
    fun alertdilog()
    {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
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
    fun alertdilog1()
    {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it)
        }


        builder!!.setMessage("Are you sure you want to SingOut ?")
            .setTitle("SingOut")

        builder.apply {
            setPositiveButton("No") { dialog, id ->
                val selectedId = id
                dialog.dismiss()
            }
            setNegativeButton("Yes") { dialog, id ->
                val selectedId = id
                val sharedPreferences: SharedPreferences =
                    getSharedPreferences("v1", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.clear()
                editor.commit()

                var intent=Intent(this@Dashbord,
                    MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        val dialog: AlertDialog? = builder.create()

        dialog!!.show()
    }

    override fun onBackPressed() {
        alertdilog()
    }
    fun makerequest() {
        ActivityCompat.requestPermissions(
            this@Dashbord!!,
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_PHONE_STATE
            ),
            RECORD_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i("@@permission", "Permission has been denied by user")
                    Toast.makeText(this@Dashbord,"Please Allow Permission", Toast.LENGTH_LONG).show()
//                    makerequest()
                } else {
                    Log.i("@@permission", "Permission has been granted by user")

                }
            }
        }
    }
}