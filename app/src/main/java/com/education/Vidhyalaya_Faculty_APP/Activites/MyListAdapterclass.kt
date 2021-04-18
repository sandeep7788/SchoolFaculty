package com.education.Vidhyalaya_Faculty_APP.Activites

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.education.Vidhyalaya_Faculty_APP.R
import com.education.Vidhyalaya_Faculty_APP.API.Faculty.Classlist

class MyListAdapterclass(private val context: Activity, private val title: ArrayList<Classlist>)
    : ArrayAdapter<Classlist>(context, R.layout.custom_list, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView

        titleText.text = title[position].class_

        return rowView
    }
}