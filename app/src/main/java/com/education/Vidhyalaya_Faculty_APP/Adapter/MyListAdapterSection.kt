package com.education.Vidhyalaya_Faculty_APP.Adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.Section
import com.education.Vidhyalaya_Faculty_APP.R

class MyListAdapterSection(private val context: Activity, private val title: ArrayList<Section>)
    : ArrayAdapter<Section>(context, R.layout.custom_list, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView

        titleText.text = title[position].section

        return rowView
    }
}