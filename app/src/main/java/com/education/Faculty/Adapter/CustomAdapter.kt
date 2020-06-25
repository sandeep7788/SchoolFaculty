package com.education.Faculty.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.dating.schoolfaculty.R
import com.education.Faculty.API.Faculty.Studentlistapi
import java.util.*

class CustomAdapter(private val dataSet: ArrayList<Studentlistapi>, mContext: Context) :
    ArrayAdapter<Studentlistapi>(mContext, R.layout.listitem, dataSet) {
    private class ViewHolder {
        lateinit var txtName: TextView
        lateinit var checkBox: CheckBox
    }
    override fun getCount(): Int {
        return dataSet.size
    }
    override fun getItem(position: Int): Studentlistapi {
        return dataSet[position] as Studentlistapi
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
    /*        viewHolder.txtName =
                convertView.findViewById(R.id.playerNameList)
            viewHolder.checkBox =
                convertView.findViewById(R.id.checkBox)*/
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
        val item: Studentlistapi = getItem(position)
        viewHolder.txtName.text = item.student
        viewHolder.checkBox.isChecked = item.isSelected
        return result
    }
}