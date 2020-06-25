package com.education.Faculty.Activites;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.dating.schoolfaculty.R;
import com.education.Faculty.API.Faculty.Friend;

import java.util.ArrayList;

class ListViewAdapter1 extends ArrayAdapter<Friend> {

    private Activity activity;
    private ArrayList<Friend> Friends;
    private final String TAG = "@@";

    public ListViewAdapter1(Activity activity, int resource, ArrayList<Friend> Friends) {
        super(activity, resource, Friends);
        this.activity = activity;
        this.Friends = Friends;
        Log.i(TAG, "init adapter");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     ListViewAdapter1.ViewHolder holder = null;

        // inflate layout from xml
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.listitem, parent, false);
            // get all UI view
            holder = new ListViewAdapter1.ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ListViewAdapter1.ViewHolder) convertView.getTag();
        }

        Friend friend = Friends.get(position);

        //set Friend data to views
        holder.name.setText(friend.getName());

        //set event for checkbox
        holder.check.setOnCheckedChangeListener(onCheckedChangeListener(friend));

        return convertView;
    }

    /**
     * handle check box event
     * @param f
     * @return
     */
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener(final Friend f) {
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    f.setSelected(true);
                } else {
                    f.setSelected(false);
                }
            }
        };
    }

    private class ViewHolder
    {
        private TextView name;
        private AppCompatCheckBox check;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.name);
            check = (AppCompatCheckBox) v.findViewById(R.id.check);
        }
    }
}